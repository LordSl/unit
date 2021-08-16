import com.lordsl.unit.common.Container;
import com.lordsl.unit.test.App;
import com.lordsl.unit.test.config.FlowTheta;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
public class TestConfig {

    @Test
    void cal() {
        Container res = new FlowTheta().exec();
    }

}
