import com.lordsl.unit.test.App;
import com.lordsl.unit.test.nest.A1;
import com.lordsl.unit.test.nest.NestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
public class TestNest {
    @Autowired
    A1 a1;
    @Autowired
    NestConfig nestConfig;

    @Test
    void test() {
        a1.execute();
    }

}
