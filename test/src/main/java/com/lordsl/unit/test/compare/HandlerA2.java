package com.lordsl.unit.test.compare;

import java.util.List;

public class HandlerA2 {
    public task2Resp handle(task2Req req) {
        Long f = 0L;
        String d = String.valueOf(req.c.get(0));
        long e = (long) req.c.get(0).hashCode() * 101;
        for (Object o : req.c) {
            long tmp = o.hashCode() * d.hashCode() / (e + 1);
//            Info.BlueInfo(Long.toString(tmp));
            f += tmp;
        }
        return new task2Resp(f);
    }

    public static class task2Req {
        public List<Object> c;

        public task2Req(List<Object> c) {
            this.c = c;
        }
    }

    public static class task2Resp {
        public Long f;

        public task2Resp(Long f) {
            this.f = f;
        }
    }

}
