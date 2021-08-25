package com.lordsl.unit.common.condition;

import com.lordsl.unit.common.util.Container;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class Condition {
    protected static final AtomicInteger defaultNameCount = new AtomicInteger(0);
    protected static final Map<String, Condition> nameMap = new HashMap<>();
    protected static final Predicate<Container> TruePredicate = (zone -> true);

    protected String name;
    private Predicate<Container> predicate;

    protected Condition() {
        this.name("#" + defaultNameCount.incrementAndGet());
        this.setPredicate(TruePredicate);
        nameMap.put(name, this);
    }

    protected Condition(String name) {
        this.name(name);
        this.setPredicate((zone) -> true);
    }

    public static Condition create(String key) {
        return new Condition(key);
    }

    public static Condition create() {
        return new Condition();
    }

    public Condition get(String conditionKey) {
        return nameMap.get(conditionKey);
    }

    public static Condition not(Condition other) {
        Condition tmp = new Condition();
        tmp.setPredicate((zone) -> !other.getPredicate().test(zone));
        return tmp;
    }

    public static Condition and(Condition A, Condition B) {
        Condition tmp = new Condition();
        tmp.setPredicate((zone) -> A.getPredicate().test(zone) && B.getPredicate().test(zone));
        return tmp;
    }

    public static Condition or(Condition A, Condition B) {
        Condition tmp = new Condition();
        tmp.setPredicate((zone) -> A.getPredicate().test(zone) || B.getPredicate().test(zone));
        return tmp;
    }

    public Predicate<Container> getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate<Container> predicate) {
        this.predicate = predicate;
    }

    public Condition name(String name) {
        this.name = name;
        return this;
    }

    public Condition not() {
        this.setPredicate((zone) -> !getPredicate().test(zone));
        return this;
    }

    public Condition and(Condition other) {
        this.setPredicate((zone) -> other.getPredicate().test(zone) && this.getPredicate().test(zone));
        return this;
    }

    public Condition or(Condition other) {
        this.setPredicate((zone) -> other.getPredicate().test(zone) || this.getPredicate().test(zone));
        return this;
    }
}
