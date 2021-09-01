import com.lordsl.unit.compiler.TokenCompiler;
import com.lordsl.unit.compiler.TokenSchema;
import com.lordsl.unit.util.Container;
import com.lordsl.unit.util.Info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TestCompiler {

    @Test
    void testPredicate() {
        String input = "( ( a + 1 ) > 3 ) & ( b > 0 ) & ( ! ( ( b = 1 ) | ( b = 1.2 ) ) )";

        Predicate<Container> predicate = TokenCompiler.compile2Predicate(input);
        Container env = new Container();
        env.put("a", new BigDecimal(3));
        env.put("b", 2);
        Info.BlueInfo(String.valueOf(predicate.test(env)));
        Info.YellowText(TokenCompiler.norm(input));
    }

    @Test
    void testConsumer() {
        String input = "c := ( ( a + 1 ) * ( ( b - 0.12 ) / 3 ) )";

        Consumer<Container> consumer = TokenCompiler.compile2Consumer(input);
        Container env = new Container();
        env.put("a", new BigDecimal(3));
        env.put("b", 2);
        consumer.accept(env);
        Number c = env.get("c");
        Info.BlueInfo(String.valueOf(c));
        Info.YellowText(TokenCompiler.norm(input));
    }

    @Test
    void trans1() {
        String input = "c := 1";
        String json = TokenCompiler.plain2JSON(input);
        TokenSchema schema = TokenCompiler.JSON2Schema(json);
        Assertions.assertEquals(json, TokenCompiler.schema2JSON(schema));
        Consumer<Container> consumer1 = TokenCompiler.compile2Consumer(input);
        Consumer<Container> consumer2 = TokenCompiler.compile2Consumer(schema);
        Container env1 = new Container();
        consumer1.accept(env1);
        Container env2 = new Container();
        consumer2.accept(env2);
        Number c1 = env1.get("c");
        Number c2 = env2.get("c");
        Assertions.assertNotNull(c1);
        Assertions.assertEquals(c1, c2);
        Info.YellowText(TokenCompiler.norm(input));
    }

    @Test
    void trans2() {
        String input = "d = 1";
        String json = TokenCompiler.plain2JSON(input);
        TokenSchema schema = TokenCompiler.JSON2Schema(json);
        Predicate<Container> predicate = TokenCompiler.compile2Predicate(schema);
        Container env = new Container();
        env.put("d", 1);
        Assertions.assertTrue(predicate.test(env));
        Info.YellowText(TokenCompiler.norm(input));
        Assertions.assertEquals(TokenCompiler.JSON2Plain(json), TokenCompiler.norm(input));

    }
}
