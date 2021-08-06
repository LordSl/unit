package com.lordsl.unit.common;

import com.lordsl.unit.common.anno.Handle;
import com.lordsl.unit.common.anno.Uni;
import com.lordsl.unit.common.anno.Unit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Resolver {
    HandlerModel model;
    Map<String, Field> consumes, produces, throughs, refers;
    Set<Method> handles;
    Function<Void, HandlerModel> getTemplate;
    Map<String, Field> inputs, deletes, outputs;
    Unit unit;

    Resolver(HandlerModel model) {
        this.model = model;
        Class<?> cla = model.getClass();
        consumes = PublicFunc.getConsumesFields.apply(cla);
        produces = PublicFunc.getProducesFields.apply(cla);
        throughs = PublicFunc.getThroughsFields.apply(cla);
        refers = PublicFunc.getRefersFields.apply(cla);
        handles = PublicFunc.getHandlesSet.apply(cla);
        getTemplate = model.getTemplate();
        unit = cla.getAnnotation(Unit.class);
    }

    private void setAllAccessible() {
        Stream.of(consumes.values(), produces.values(), throughs.values(), refers.values())
                .flatMap(Collection::stream)
                .forEach(
                        field -> field.setAccessible(true)
                );
    }

    private void resolveRefers() {
        ReferInjectManager.addTask((Void) -> {
            refers.forEach((name, field) -> {
                try {
                    field.setAccessible(true);
                    Dictator.putRefer(name, field.get(model));
                } catch (Exception e) {
                    Info.PurpleAlert("refer inject exception");
                }
            });
            return null;
        });
    }

    private void resolveOpsWithContainer() {
        //consumes + throughs
        inputs = Stream.concat(
                consumes.entrySet().stream(), throughs.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        //consumes - throughs
        deletes = consumes.entrySet()//through优先级高
                .stream()
                .filter(item -> !throughs.entrySet().contains(item))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        //produces + throughs
        outputs = Stream.concat(
                produces.entrySet().stream(), throughs.entrySet().stream())//是引用类型就不用放回
                .filter(item -> !PublicFunc.isReference.apply(item.getValue().getType()) || !throughs.entrySet().contains(item))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }


    private void resolveAllMethods() {
        Map<Class<?>, Function<Container, Container>> map = getFunctionForEachFlow();
        Arrays.stream(unit.unis()).forEach(
                uni -> {
                    Class<?> flow = uni.flow();
                    Float order = Float.parseFloat(uni.order());
                    Node node = new Node(order, flow, map.get(flow));
                    Dictator.putNode(flow, node);
                }
        );
    }

    Map<Class<?>, Function<Container, Container>> getFunctionForEachFlow() {
        Map<Class<?>, List<Method>> map = new HashMap<>();
        List<Class<?>> flowsDefault = Arrays.stream(unit.unis()).map(Uni::flow).collect(Collectors.toList());
        //归档
        handles.forEach(
                method -> {
                    Handle handle = method.getAnnotation(Handle.class);
                    List<Class<?>> flows = handle.flows().length == 0 ? flowsDefault : Arrays.asList(handle.flows());
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
        Map<Class<?>, Function<Container, Container>> res = new HashMap<>();
        map.keySet().forEach(
                key -> res.put(key, buildFunc(map.get(key)))
        );
        return res;
    }


    private Function<Container, Container> buildFunc(List<Method> methods) {
        return (container) -> {
            try {
                HandlerModel instance = getTemplate.apply(null);

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
                Info.PurpleAlert("param inject exception");
            }
            return null;
        };
    }

    public void resolve() {
        setAllAccessible();
        resolveRefers();
        resolveOpsWithContainer();
        resolveAllMethods();
    }

}
