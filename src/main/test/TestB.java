import com.lordsl.unit.App;
import com.lordsl.unit.common.Info;
import com.lordsl.unit.test.FlowB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(classes = App.class)
public class TestB {
    @Autowired
    FlowB flow;
    int CON_NUM = 2000;

    @Test
    void cal() {
        long t1 = System.currentTimeMillis();

        Object o = new Object();
        AtomicInteger res = new AtomicInteger();
        for (int i = 0; i < CON_NUM; i++)
            new Thread(
                    () -> {
                        flow.exec();
                        res.getAndIncrement();
                        if (res.get() == CON_NUM) {
                            long t2 = System.currentTimeMillis();
                            Info.GreenLog("time->" + (t2 - t1));
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
            Info.PurpleAlert("interrupt");
        }
    }

}
