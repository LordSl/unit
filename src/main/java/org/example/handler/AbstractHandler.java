package org.example.handler;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractHandler {
    Manager manager;

    @Autowired
    public AbstractHandler(Manager manager) {
        if (!(manager instanceof Marker)) {
            this.manager = manager;
            regis();
        }
    }


    public void regis() {
        Class<?> cla = this.getClass();
        Unit unit = cla.getAnnotation(Unit.class);
        if (unit == null) return;
        //优先级
        Float order = Float.parseFloat(unit.order());
        try {
            Function<Container, Container> function = buildFunction();

            if (null != manager)
                manager.put(new HandlerUnit(cla, order, function));

        } catch (NoSuchMethodException ignored) {
            ;
        }
    }

    Function<Container, Container> buildFunction() throws NoSuchMethodException {
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

        Method handle = cla.getDeclaredMethod("handle");

        Function<Container, Container> function = (container) -> {
            try {
                Object instance = cla.getConstructor(Manager.class).newInstance(Marker.getInstance());

                Collection<Field> ifs = inputs.values();
                Collection<Field> ofs = outputs.values();

                //从容器输入
                for (Field f : ifs) {
                    f.set(instance, container.get(f.getName()));
                    container.remove(f.getName());
                }

                //执行计算
                handle.invoke(instance);

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

        return function;
    }

    abstract void handle();


}
