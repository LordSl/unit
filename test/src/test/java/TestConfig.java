import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.common.util.Container;
import com.lordsl.unit.common.util.Info;
import com.lordsl.unit.test.App;
import com.lordsl.unit.test.config.FlowTheta;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
public class TestConfig {

    @Test
    void cal() {
        Info.BlueInfo(OpFacade.getAllNodeImpl("com.lordsl.unit.test.example").toString());
        Container res = new FlowTheta().exec();
    }

}
