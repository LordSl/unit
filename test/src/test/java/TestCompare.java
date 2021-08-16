import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.test.App;
import com.lordsl.unit.test.compare.FlowA;
import com.lordsl.unit.test.compare.FlowB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(classes = App.class)
public class TestCompare {
    @Autowired
    FlowA flowA;
    @Autowired
    FlowB flowB;
    int CON_NUM = 2000;


    @Test
    void calA() {
        long t1 = System.currentTimeMillis();

        Object o = new Object();
        AtomicInteger res = new AtomicInteger();
        for (int i = 0; i < CON_NUM; i++)
            new Thread(
                    () -> {
                        flowA.exec();
                        res.getAndIncrement();
                        if (res.get() == CON_NUM) {
                            long t2 = System.currentTimeMillis();
                            OpFacade.GreenLog("time->" + (t2 - t1));
                            synchronized (o) {
                                o.notifyAll();
                            }
                        }
                    }
            ).start();
        try {
            synchronized (o) {
                o.wait();
            }
        } catch (Exception e) {
            OpFacade.PurpleAlert("interrupt");
        }
    }

    @Test
    void calB() {
        long t1 = System.currentTimeMillis();

        Object o = new Object();
        AtomicInteger res = new AtomicInteger();
        for (int i = 0; i < CON_NUM; i++)
            new Thread(
                    () -> {
                        flowB.exec();
                        res.getAndIncrement();
                        if (res.get() == CON_NUM) {
                            long t2 = System.currentTimeMillis();
                            OpFacade.GreenLog("time->" + (t2 - t1));
                            synchronized (o) {
                                o.notifyAll();
                            }
                        }
                    }
            ).start();
        try {
            synchronized (o) {
                o.wait();
            }
        } catch (Exception e) {
            OpFacade.PurpleAlert("interrupt");
        }
    }

}
