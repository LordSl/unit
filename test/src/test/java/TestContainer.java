import com.lordsl.unit.util.Container;
import org.junit.jupiter.api.Test;

public class TestContainer {

    @Test
    void test() {
        Container bomb = new Container();
        bomb.put("bomb", bomb);
        Container bombNest = bomb.get("bomb");
        bombNest.put("bombNest", bombNest);
    }

}
