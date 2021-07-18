package org.example.handler;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Manager {
    List<HandlerUnit> units;

    Manager() {
        units = new LinkedList<>();
        //插入屏障
        units.add(new HandlerUnit(null, Float.MAX_VALUE, null));
    }

    public void put(HandlerUnit newUnit) {
        int index = 0;
        for (; index < units.size(); index++) {
            HandlerUnit unit = units.get(index);
            //不允许order相等
            if (unit.getOrder().equals(newUnit.getOrder()))
                return;
            if (unit.getOrder() > newUnit.getOrder())
                break;
        }
        float tmp = index > 0 ? newUnit.getOrder() - units.get(index - 1).getOrder() : 0;
        if (0 < tmp && tmp <= 0.1)
            units.set(index - 1, newUnit);
        else
            units.add(index, newUnit);
    }

    public Object exec() {
        Container container = new Container();
        container = exec(container);
        //todo 从容器中取数据，构造response
        return "res";
    }

    private Container exec(Container container) {
        //忽略最后一个
        for (int i = 0; i < units.size() + 1; i++)
            container = units.get(i).getFunction().apply(container);
        return container;
    }

}
