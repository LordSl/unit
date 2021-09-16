package com.lordsl.unit.state;

import com.lordsl.unit.compiler.LogicContextModel;

public interface LogicRouterModel {

    BackwardState getNextHop(LogicContextModel logicContextModel);

}
