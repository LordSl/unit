import org.com.lordsl.unit.App;
import org.com.lordsl.unit.common.Info;
import org.com.lordsl.unit.example.FlowAlpha;
import org.com.lordsl.unit.example.FlowBeta;
import org.com.lordsl.unit.common.Container;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
public class Test1 {
    @Autowired
    FlowAlpha flowAlpha;
    @Autowired
    FlowBeta flowBeta;

    @Test
    void t1() {
        Container res1 = flowAlpha.exec();
        Container res2 = flowBeta.exec();
        Info.outToJson();
        System.out.println("ok");
    }

}
