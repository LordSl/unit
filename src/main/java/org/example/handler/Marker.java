package org.example.handler;

public class Marker extends Manager {
    private static final Marker marker = new Marker();

    public static Marker getInstance() {
        return marker;
    }
}
