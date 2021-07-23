package org.com.lordsl.unit.common;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractHandler {

    protected AbstractHandler() {
        if (Signal.isPrepare()) {
            regis();
        }
    }

    private void regis() {
        Class<?> cla = this.getClass();
        Unit unit = cla.getAnnotation(Unit.class);
        if (unit == null) return;
        //优先级
        List<Float> orders = Arrays.stream(unit.order()).map(Float::parseFloat).collect(Collectors.toList());
        List<Class<?>> flows = Arrays.stream(unit.flow()).collect(Collectors.toList());

        int len = orders.size();
        if (len != flows.size())
            return;

        Function<Container, Container> function = buildFunction();

        for (int index = 0; index < len; index++) {
            Float order = orders.get(index);
            Class<?> flow = flows.get(index);
            Dictator.put(new HandlerUnit(cla, order, function), flow);
        }
    }

    private Function<Container, Container> buildFunction() {

        //Through的效果会覆盖掉Consume和Produce
        Map<String, Field> consumes = getConsumes();
        Map<String, Field> produces = getProduces();
        Map<String, Field> throughs = getThroughs();

        //解除protected和private
        for (Field f : consumes.values()) {
            f.setAccessible(true);
        }
        for (Field f : produces.values()) {
            f.setAccessible(true);
        }
        for (Field f : throughs.values()) {
            f.setAccessible(true);
        }

        //consumes + throughs
        Map<String, Field> inputs = Stream.concat(
                consumes.entrySet().stream(), throughs.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        //consumes - throughs
        Map<String, Field> deletes = consumes.entrySet()
                .stream()
                .filter(item -> !throughs.entrySet().contains(item))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        //produces + throughs
        Map<String, Field> outputs = Stream.concat(
                produces.entrySet().stream(), throughs.entrySet().stream())
                .filter(item -> item.getValue().getType().isPrimitive())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        Class<?> cla = this.getClass();
        return (container) -> {
            try {
                Signal.setRuntime();//信号量，关闭注册
                AbstractHandler instance = (AbstractHandler) cla.newInstance();

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
                instance.handle();

                //向容器输出
                for (String name : outputs.keySet()) {
                    Field f = outputs.get(name);
                    container.put(name, f.get(instance));
                }

                return container;

            } catch (Exception ignored) {
                ;
            }
            return null;
        };
    }

    Map<String, Field> getProduces() {
        Field[] fields = this.getClass().getDeclaredFields();
        Map<String, Field> produces = new HashMap<>();

        for (Field field : fields) {
            Produce produce = field.getAnnotation(Produce.class);
            if (null != produce) {
                produces.put(produce.name().equals("") ? field.getName() : produce.name(), field);
            }
        }
        return produces;
    }

    Map<String, Field> getConsumes() {
        Field[] fields = this.getClass().getDeclaredFields();
        Map<String, Field> consumes = new HashMap<>();

        for (Field field : fields) {
            Consume consume = field.getAnnotation(Consume.class);
            if (null != consume) {
                consumes.put(consume.name().equals("") ? field.getName() : consume.name(), field);
            }
        }
        return consumes;
    }

    Map<String, Field> getThroughs() {
        Field[] fields = this.getClass().getDeclaredFields();
        Map<String, Field> throughs = new HashMap<>();

        for (Field field : fields) {
            Through through = field.getAnnotation(Through.class);
            if (null != through) {
                throughs.put(through.name().equals("") ? field.getName() : through.name(), field);
            }
        }
        return throughs;
    }

    public abstract void handle();


}
