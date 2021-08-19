import com.lordsl.unit.common.Container;
import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.test.App;
import com.lordsl.unit.test.config.FlowTheta;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
public class TestConfig {

    @Test
    void cal() {
        OpFacade.BlueInfo(OpFacade.getAllFlowImpl("com.lordsl.unit.test.example").toString());
        OpFacade.BlueInfo(OpFacade.getAllHandlerImpl("com.lordsl.unit.test.example").toString());
        Container res = new FlowTheta().exec();
    }

}
