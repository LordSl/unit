package com.lordsl.unit.common;

import com.lordsl.unit.common.anno.Consume;
import com.lordsl.unit.common.anno.Produce;
import com.lordsl.unit.common.anno.Refer;
import com.lordsl.unit.common.anno.Through;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PublicFunc {
    final static Function<Map<String, Field>, Map<String, Class<?>>> convertMap = (map) -> Stream.of(
            map.entrySet())
            .flatMap(Collection::stream)
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    set -> set.getValue().getType()
            ));
    final static Function<Class<?>, Map<String, Field>> getProducesFields = (Class<?> cla) -> {
        Field[] fields = cla.getDeclaredFields();
        Map<String, Field> produces = new HashMap<>();

        for (Field field : fields) {
            Produce produce = field.getAnnotation(Produce.class);
            if (null != produce) {
                produces.put(produce.name().equals("") ? field.getName() : produce.name(), field);
            }
        }
        return produces;
    };
    final static Function<Class<?>, Map<String, Field>> getConsumesFields = (Class<?> cla) -> {
        Field[] fields = cla.getDeclaredFields();
        Map<String, Field> consumes = new HashMap<>();

        for (Field field : fields) {
            Consume consume = field.getAnnotation(Consume.class);
            if (null != consume) {
                consumes.put(consume.name().equals("") ? field.getName() : consume.name(), field);
            }
        }
        return consumes;
    };
    final static Function<Class<?>, Map<String, Field>> getThroughsFields = (Class<?> cla) -> {
        Field[] fields = cla.getDeclaredFields();
        Map<String, Field> throughs = new HashMap<>();
        for (Field field : fields) {
            Through through = field.getAnnotation(Through.class);
            if (null != through) {
                throughs.put(through.name().equals("") ? field.getName() : through.name(), field);
            }
        }
        return throughs;
    };

    final static Function<Class<?>, Map<String, Field>> getRefersFields = (Class<?> cla) -> {
        Field[] fields = cla.getDeclaredFields();
        Map<String, Field> refers = new HashMap<>();
        for (Field field : fields) {
            Refer refer = field.getAnnotation(Refer.class);
            if (null != refer) {
                refers.put(refer.name().equals("") ? field.getName() : refer.name(), field);
            }
        }
        return refers;
    };

    final static Function<Class<?>, Function<Container, Container>> getConductFunction = (Class<?> cla) -> {

        //Through的效果会覆盖掉Consume和Produce
        Map<String, Field> consumes = PublicFunc.getConsumesFields.apply(cla);
        Map<String, Field> produces = PublicFunc.getProducesFields.apply(cla);
        Map<String, Field> throughs = PublicFunc.getThroughsFields.apply(cla);
        Map<String, Field> refers = PublicFunc.getRefersFields.apply(cla);

        List<Field> properties = Stream.of(consumes.values(), produces.values(), throughs.values(), refers.values())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        //解除protected和private
        for (Field f : properties) {
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
        Map<String, Field> deletes = consumes.entrySet()//through优先级高
                .stream()
                .filter(item -> !throughs.entrySet().contains(item))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        //produces + throughs
        Map<String, Field> outputs = Stream.concat(
                produces.entrySet().stream(), throughs.entrySet().stream())//是引用类型就不用放回
                .filter(item -> !isReference(item.getValue().getType()) || !throughs.entrySet().contains(item))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        return (container) -> {
            try {
                HandlerModel instance = (HandlerModel) cla.newInstance();

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
                instance.handle();

                //向容器输出
                for (String name : outputs.keySet()) {
                    Field f = outputs.get(name);
                    container.put(name, f.get(instance));
                }

                return container;

            } catch (Exception ignored) {
                Info.PurpleAlert("param inject exception");
            }
            return null;
        };
    };

    static boolean isReference(Class<?> cla) {
        if (cla.isPrimitive()) return false;
        try {
            if (((Class<?>) (cla.getField("TYPE").get(null))).isPrimitive())
                return false;
        } catch (Exception ignored) {
            ;
        }
        return true;
    }

}