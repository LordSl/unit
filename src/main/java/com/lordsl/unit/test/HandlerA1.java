package com.lordsl.unit.test;

import java.util.ArrayList;
import java.util.List;

public class HandlerA1 {

    public task1Resp handle(task1Req req) {
        ArrayList<Object> c = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            c.add(req.a * req.b.hashCode() % (i + 1));
        }
        return new task1Resp(c);
    }

    public static class task1Req {
        public Integer a;
        public String b;

        public task1Req(Integer a, String b) {
            this.a = a;
            this.b = b;
        }
    }

    public static class task1Resp {
        public List<Object> c;

        public task1Resp(ArrayList<Object> c) {
            this.c = c;
        }
    }


}
