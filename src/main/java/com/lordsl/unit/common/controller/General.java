package com.lordsl.unit.common.controller;

import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class General {
    private static final Map<Pair, Function<Pair, Void>> cache = new HashMap<>();
    private static final Map<Long, Locator> locatorMap = new HashMap<>();
    private Locator locator = null;

    private static Function<Pair, Void> buildCallBetween(Pair pair) {
        //从src取出container
        //注入到des
        //des function执行
        return null;
    }

    public static void call(HandlerModel src, Class<?> target) {
        Pair thisPair = new Pair(src, target);
        if (cache.containsKey(thisPair)) {
            cache.get(thisPair).apply(thisPair);
        } else {
            Function<Pair, Void> func = buildCallBetween(thisPair);
            cache.put(thisPair, func);
            func.apply(thisPair);
        }
    }

    public static void plan(HandlerModel src, Class<?>... targets) {
        Long id = Thread.currentThread().getId();
        locatorMap.merge(id, new Locator(targets, null, 1000), (o, n) -> new Locator(targets, o, 1000));
    }

    public Node getNext() {
        //取得下一个
        return null;
    }

    private static class Pair {
        HandlerModel src;
        Class<?> target;

        Pair(HandlerModel src, Class<?> target) {
            this.src = src;
            this.target = target;
        }

    }

    private static class Locator {
        //需调用的handler
        Class<?>[] targets;

        //若不是嵌套，则为空
        Locator last;

        //可重入次数
        Integer RTimes;

        Integer index = 0;

        public Locator(Class<?>[] targets, Locator last, Integer RTimes) {
            this.targets = targets;
            this.last = last;
            this.RTimes = RTimes;
        }

    }
}
