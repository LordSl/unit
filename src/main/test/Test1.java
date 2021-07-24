import com.lordsl.unit.App;
import com.lordsl.unit.common.Container;
import com.lordsl.unit.example.FlowAlpha;
import com.lordsl.unit.example.FlowBeta;
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
    void cal() {
        Container res1 = flowAlpha.exec();
        Container res2 = flowBeta.exec();
//        Info.outToJson();
        System.out.println("ok");
    }

}
