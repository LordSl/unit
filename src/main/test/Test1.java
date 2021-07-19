import org.example.App;
import org.example.handler.Container;
import org.example.handler.Manager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
public class Test1 {
    @Autowired
    Manager manager;

    @Test
    void t1() {
        Container res = manager.exec();
        System.out.println("ok");
    }


}
