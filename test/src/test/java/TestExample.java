import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.common.util.Container;
import com.lordsl.unit.test.App;
import com.lordsl.unit.test.example.FlowAlpha;
import com.lordsl.unit.test.example.FlowBeta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest(classes = App.class)
public class TestExample {
    @Autowired
    FlowAlpha flowAlpha;
    @Autowired
    FlowBeta flowBeta;

    String getRootPath() {
        try {
            return new File("").getCanonicalPath();
        } catch (Exception e) {
            return null;
        }
    }

    @Test
    void cal() {
        Container res1 = flowAlpha.exec();
        Container res2 = flowBeta.exec();
        OpFacade.outToJson(getRootPath() + "/schema.json");
        System.out.println("ok");
    }

}
