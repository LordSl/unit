package com.lordsl.unit.state;

import com.lordsl.unit.compiler.LogicContextModel;
import com.lordsl.unit.compiler.TokenCompiler;
import com.lordsl.unit.state.schema.RouteSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LogicRouter implements LogicRouterModel {
    private final List<Predicate<LogicContextModel>> pList = new ArrayList<>();
    private final List<BackwardState> sList = new ArrayList<>();
    private List<RouteSchema> routeList;

    private LogicRouter() {
    }

    public static LogicRouter from(List<RouteSchema> routeList) {
        LogicRouter lr = new LogicRouter();
        lr.routeList = routeList;
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
        routeList.forEach(
                item -> {
                    BackwardState state = RegisCenter.idStateMap.get(item.getToState());
                    Predicate<LogicContextModel> predicate = TokenCompiler.compile2Predicate(item.getCondition());
                    if (state != null)
                        sList.add(state);
                    if (null != predicate)
                        pList.add(predicate);
                }
        );
        if (!(sList.size() == routeList.size() && pList.size() == routeList.size()))
            return null;
        return this;
    }

}
