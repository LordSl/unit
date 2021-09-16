package com.lordsl.unit.state;

import com.lordsl.unit.state.schema.StateSchema;

import java.util.ArrayList;
import java.util.List;

public class BackwardState {
    private final List<BizExecutorModel> eList = new ArrayList<>();
    private StateSchema schema;
    private LogicRouterModel lr;

    private BackwardState() {
    }

    public static BackwardState from(StateSchema schema) {
        BackwardState state = new BackwardState();
        state.schema = schema;
        RegisCenter.idStateMap.put(schema.getId(), state);
        return state;
    }

    public BackwardState accept(BizContextModel bizContextModel) {
        eList.forEach(e -> e.execOn(bizContextModel));
        return lr.getNextHop(bizContextModel.getLogicContext());
    }

    public BackwardState build() {
        LogicRouterModel router = LogicRouter.from(schema.getRouteList()).build();
        if (null == router)
            return null;
        lr = router;
        schema.getExecList().forEach(
                item -> {
                    BizExecutorModel executorModel = RegisCenter.idExecMap.get(item);
                    if (null != executorModel)
                        eList.add(executorModel);
                }
        );
        if (eList.size() != schema.getExecList().size())
            return null;
        return this;
    }

}
