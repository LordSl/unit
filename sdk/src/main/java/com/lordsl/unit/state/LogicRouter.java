package com.lordsl.unit.state;

import com.lordsl.unit.compiler.LogicContextModel;
import com.lordsl.unit.compiler.TokenCompiler;
import com.lordsl.unit.state.schema.RouterSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LogicRouter implements LogicRouterModel {
    private final List<Predicate<LogicContextModel>> pList = new ArrayList<>();
    private final List<BackwardState> sList = new ArrayList<>();
    private RouterSchema schema;

    private LogicRouter() {
    }

    public static LogicRouter from(RouterSchema schema) {
        LogicRouter lr = new LogicRouter();
        lr.schema = schema;
        RegisCenter.idRouterMap.put(schema.getId(), lr);
        return lr;
    }

    @Override

    public BackwardState getNextHop(LogicContextModel logicContextModel) {
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).test(logicContextModel))
                return sList.get(i);
        }
        return null;
    }

    public LogicRouter build() {
        schema.getRouteList().forEach(
                item -> {
                    BackwardState state = RegisCenter.idStateMap.get(item.getToState());
                    Predicate<LogicContextModel> predicate = TokenCompiler.compile2Predicate(item.getCondition());
                    if (state != null)
                        sList.add(state);
                    if (null != predicate)
                        pList.add(predicate);
                }
        );
        if (!(sList.size() == schema.getRouteList().size() && pList.size() == schema.getRouteList().size()))
            return null;
        return this;
    }

}
