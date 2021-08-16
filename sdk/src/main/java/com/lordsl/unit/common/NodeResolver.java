package com.lordsl.unit.common;

import com.lordsl.unit.common.anno.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class NodeResolver {
    private final HandlerModel model;
    private final Map<String, Field> consumes, produces, throughs, updates, refers;
    private final Set<Method> handles;
    private final Supplier<HandlerModel> getTemplate;
    private final Unit unit;
    private Map<String, Field> inputs, deletes, outputs;

    NodeResolver(HandlerModel model) {
        this.model = model;
        Class<?> cla = model.getClass();
        consumes = ParseUtil.getAnnoFields(Consume.class, cla);
        produces = ParseUtil.getAnnoFields(Produce.class, cla);
        throughs = ParseUtil.getAnnoFields(Through.class, cla);
        updates = ParseUtil.getAnnoFields(Update.class, cla);
        refers = ParseUtil.getAnnoFields(Refer.class, cla);
        handles = ParseUtil.getAnnoMethods(Handle.class, cla);
        getTemplate = model.getTemplate();
        unit = cla.getAnnotation(Unit.class);
    }

    private void setAllAccessible() {
        Stream.of(consumes.values(), produces.values(), throughs.values(), updates.values(), refers.values())
                .flatMap(Collection::stream)
                .forEach(
                        field -> field.setAccessible(true)
                );
        handles.forEach(
                method -> method.setAccessible(true)
        );
    }

    private void resolveRefers() {
        TaskResolver.addReferTask(() ->
                refers.forEach((name, field) -> {
                    try {
                        field.setAccessible(true);
                        Dictator.putRefer(name, field.get(model));
                    } catch (Exception e) {
                        Info.PurpleAlert("refer inject exception");
                    }
                })
        );
    }

    private void resolveOpsWithContainer() {
        //consumes + throughs + updates
        inputs = Stream.of(
                consumes.entrySet(), throughs.entrySet(), updates.entrySet())
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        //consumes - throughs - updates
        deletes = consumes.entrySet().stream()
                .filter(item -> !throughs.entrySet().contains(item) && !updates.entrySet().contains(item))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        //produces + updates
        outputs = Stream.of(
                produces.entrySet(), updates.entrySet())
                .flatMap(Collection::stream)
                .filter(item -> !((ParseUtil.isReferenceExceptString(item.getValue().getType()) && updates.entrySet().contains(item))))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }


    private void resolveAllMethods() {
        Map<Class<? extends FlowModel>, Function<Container, Container>> map = getFunctionForEachFlow();
        Arrays.stream(unit.unis()).forEach(
                uni -> {
                    Class<? extends FlowModel> flow = uni.flow();
                    Float order = Float.parseFloat(uni.order());
                    Node node = new Node(order, model.getClass(), map.get(flow));
                    Dictator.putNode(flow, node);
                }
        );
    }

    Map<Class<? extends FlowModel>, Function<Container, Container>> getFunctionForEachFlow() {
        Map<Class<? extends FlowModel>, List<Method>> map = new HashMap<>();
        List<Class<? extends FlowModel>> flowsDefault = Arrays.stream(unit.unis()).map(Uni::flow).collect(Collectors.toList());
        //归档
        handles.forEach(
                method -> {
                    Handle handle = method.getAnnotation(Handle.class);
                    List<Class<? extends FlowModel>> flows = handle.flows().length == 0 ? flowsDefault : Arrays.asList(handle.flows());
                    flows.forEach(
                            flow -> {
                                map.putIfAbsent(flow, new ArrayList<>());
                                List<Method> toPut = map.get(flow);
                                toPut.add(method);
                            }
                    );
                }
        );
        //排序
        map.values().forEach(
                list -> list.sort(
                        (m1, m2) -> {
                            Float pos1 = Float.parseFloat(m1.getAnnotation(Handle.class).pos());
                            Float pos2 = Float.parseFloat(m2.getAnnotation(Handle.class).pos());
                            return pos1.compareTo(pos2);
                        }
                )
        );

        //转换为function
        Map<Class<? extends FlowModel>, Function<Container, Container>> res = new HashMap<>();
        map.keySet().forEach(
                key -> res.put(key, buildFunc(map.get(key)))
        );
        return res;
    }


    private Function<Container, Container> buildFunc(List<Method> methods) {
        return (container) -> {
            try {
                HandlerModel instance = getTemplate.get();

                //bean注入
                for (String name : refers.keySet()) {
                    Field f = refers.get(name);
                    f.set(instance, Dictator.getRefer(name));
                }

                //从容器输入
                for (String name : inputs.keySet()) {
                    Field f = inputs.get(name);
                    f.set(instance, container.get(name));
                }

                //从容器删除
                for (String name : deletes.keySet()) {
                    container.remove(name);
                }

                //执行计算
                for (Method method : methods) {
                    method.invoke(instance);
                }

                //向容器输出
                for (String name : outputs.keySet()) {
                    Field f = outputs.get(name);
                    container.put(name, f.get(instance));
                }

                return container;

            } catch (Exception e) {
                Info.PurpleAlert("final func running exception");
            }
            return null;
        };
    }

    public void resolve() {
        setAllAccessible();
        checkConflicts();
        resolveRefers();
        resolveOpsWithContainer();
        resolveAllMethods();
    }

    private void checkConflicts() {
        Map<String, Integer> tmp = new HashMap<>();
        Stream.of(consumes.entrySet(), produces.entrySet(), throughs.entrySet(), updates.entrySet(), refers.entrySet())
                .flatMap(Collection::stream)
                .forEach(
                        entry -> tmp.merge(entry.getKey(), 1, (o, n) -> o + 1)
                );
        tmp.forEach(
                (key, value) -> {
                    if (value > 1)
                        Info.PurpleAlert(String.format("key %s has more than 1 specific annotation which may cause error", key));
                }
        );

    }

}
