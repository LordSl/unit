package com.lordsl.unit.state;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegisCenter {
    public final static Map<String, BackwardState> idStateMap = new ConcurrentHashMap<>();
    public final static Map<String, BizExecutorModel> idExecMap = new ConcurrentHashMap<>();
}
