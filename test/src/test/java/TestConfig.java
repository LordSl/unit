import com.lordsl.unit.common.Container;
import com.lordsl.unit.common.OpFacade;
import com.lordsl.unit.test.App;
import com.lordsl.unit.test.config.FlowTheta;
import com.lordsl.unit.test.config.HandlerC1;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
public class TestConfig {

    @Test
    void cal() {
        OpFacade.BlueInfo(OpFacade.getAllFlowImpl(HandlerC1.class).toString());
        //由于junit的类加载器使用的根目录问题(/test-classes而非/classes)，这里直接通过包名扫描是不行的
//        OpFacade.BlueInfo(OpFacade.getAllFlowImpl("com.lordsl.unit.test.example").toString());
        OpFacade.BlueInfo(OpFacade.getAllHandlerImpl(HandlerC1.class).toString());
//        OpFacade.BlueInfo(OpFacade.getAllHandlerImpl("com.lordsl.unit.test.example").toString());
        Container res = new FlowTheta().exec();
    }

}
