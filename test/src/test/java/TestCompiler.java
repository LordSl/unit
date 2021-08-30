import com.lordsl.unit.compiler.TokenCompiler;
import com.lordsl.unit.util.Container;
import com.lordsl.unit.util.Info;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

public class TestCompiler {

    @Test
    void test() {
        String input = "( ( a + 1 ) > 3 ) & ( b > 0 )";

        Predicate<Container> predicate = TokenCompiler.compile2Predicate(input);
        Container env = new Container();
        env.put("a", 3);
        env.put("b", 1);
        Info.BlueInfo(String.valueOf(predicate.test(env)));
    }
}
