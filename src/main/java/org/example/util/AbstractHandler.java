package org.example.util;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractHandler {

    public AbstractHandler() {
        if (Signal.value == 1) {
            regis();
        }
    }

    public void regis() {
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

    Function<Container, Container> buildFunction() {
        Class<?> cla = this.getClass();

        Field[] fields = cla.getDeclaredFields();
        Map<String, Field> inputs = new HashMap<>();
        Map<String, Field> outputs = new HashMap<>();

        for (Field field : fields) {
            Input input = field.getAnnotation(Input.class);
            if (null != input)
                inputs.put(input.name().equals("") ? field.getName() : input.name(), field);
            //out-inner 别名-成员名
            Output output = field.getAnnotation(Output.class);
            if (null != output)
                outputs.put(output.name().equals("") ? field.getName() : output.name(), field);
            //out-inner 别名-成员名
        }

        Collection<Field> ifs = inputs.values();
        Collection<Field> ofs = outputs.values();

        //解除protected和private
        for (Field f : ifs) {
            f.setAccessible(true);
        }
        for (Field f : ofs) {
            f.setAccessible(true);
        }

        return (container) -> {
            try {
                Signal.value = 0;//信号量，关闭注册
                AbstractHandler instance = (AbstractHandler) cla.newInstance();

                //从容器输入
                for (Field f : ifs) {
                    f.set(instance, container.get(f.getName()));
                    container.remove(f.getName());
                }

                //执行计算
                instance.handle();

                //向容器输出
                for (Field f : ofs) {
                    container.put(f.getName(), f.get(instance));
                }

                return container;

            } catch (Exception ignored) {
                ;
            }
            return null;
        };
    }

    public abstract void handle();


}
