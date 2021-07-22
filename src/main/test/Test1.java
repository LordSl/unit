import org.com.lordsl.unit.App;
import org.com.lordsl.unit.alpha.FlowAlpha;
import org.com.lordsl.unit.common.Container;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
public class Test1 {
    @Autowired
    FlowAlpha flowAlpha;

    @Test
    void t1() {
        Container res = flowAlpha.exec();
        System.out.println("ok");
    }

}
