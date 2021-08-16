package com.lordsl.unit.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Action {
    private static final HashMap<HandlerModel, Action> interactionsMap = new HashMap<>();
    private final HandlerModel handlerModel;
    private final Set<Object> argSet = new HashSet<>();
    private boolean alreadyParse;

    private Action(HandlerModel handlerModel) {
        this.handlerModel = handlerModel;
    }

    public static Action env(HandlerModel handlerModel) {
        return interactionsMap.merge(handlerModel, new Action(handlerModel), (o, n) -> o);
    }

    public Action about(Object... args) {
        if (!alreadyParse) {
            parseArgs(args);
        }
        return this;
    }

    private void parseArgs(Object... args) {
        argSet.addAll(Arrays.asList(args));
        //todo 解析args信息，汇总到Dictator
    }

    public void conduct(Runnable conductBody) {
        alreadyParse = true;
        if (!Signal.interactionCheckEnable()) {
            conductBody.run();
        } else {
            checkInfluence(conductBody);
        }
    }

    private void checkInfluence(Runnable conductModel) {
        Object oldHash = getNotUseArgsHash();
        conductModel.run();
        Object newHash = getNotUseArgsHash();
        if (newHash == null && oldHash == null) {
            Info.PurpleAlert("old hash and new hash both null");
        } else if (newHash != null && !newHash.equals(oldHash)) {
            Info.PurpleAlert("old hash and new hash conflict, please check your conduct body.");
        }
    }

    private Object getNotUseArgsHash() {
        //todo 未使用参数的深hash
        //一个简单的办法是，递归得到所有子对象的有序集合，然后所有hash做取反运算
        //开销较大的方案，递归得到所有子对象的有序集合，然后取所有hash的BigDecimal字符串
        //开销最大但绝对准确的方案，递归得到所有子对象的唯一hash字符串（唯一标识），然后拼接
        //可以参考一下Object-Diff包
        return null;
    }

}
