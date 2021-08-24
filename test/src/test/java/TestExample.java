import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.common.util.Container;
import com.lordsl.unit.test.App;
import com.lordsl.unit.test.example.FlowAlpha;
import com.lordsl.unit.test.example.FlowBeta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

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

    String filePath = getRootPath() + "/schema.json";

    @Test
    void out() {
        Container res1 = flowAlpha.exec();
        Container res2 = flowBeta.exec();
        Assert.notNull(res1, "flowAlpha error");
        Assert.notNull(res2, "flowBeta error");
        OpFacade.outToJson(filePath);
        System.out.println("ok");
    }

}
