import com.lordsl.unit.compiler.LogicContextModel;
import com.lordsl.unit.compiler.TokenCompiler;
import com.lordsl.unit.compiler.TokenSchema;
import com.lordsl.unit.state.LogicContextContainer;
import com.lordsl.unit.util.Info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TestCompiler {

    @Test
    void testPredicate() {
        String input = "( ( ( ( a + 1 ) > 3 ) & ( b > 0 ) ) & ( ! ( ( b = 1 ) | ( b = 1.2 ) ) ) )";

        Predicate<LogicContextModel> predicate = TokenCompiler.compile2Predicate(input);
        LogicContextContainer env = new LogicContextContainer();
        env.put("a", new BigDecimal(3));
        env.put("b", 2);
        Info.BlueInfo(String.valueOf(predicate.test(env)));
        Info.YellowText(TokenCompiler.norm(input));
    }

    @Test
    void testConsumer() {
        String input = "c := ( ( a + 1 ) * ( ( b - 0.12 ) / 3 ) )";

        Consumer<LogicContextModel> consumer = TokenCompiler.compile2Consumer(input);
        LogicContextContainer env = new LogicContextContainer();
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
        Consumer<LogicContextModel> consumer1 = TokenCompiler.compile2Consumer(input);
        Consumer<LogicContextModel> consumer2 = TokenCompiler.compile2Consumer(schema);
        LogicContextContainer env1 = new LogicContextContainer();
        consumer1.accept(env1);
        LogicContextContainer env2 = new LogicContextContainer();
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
        Predicate<LogicContextModel> predicate = TokenCompiler.compile2Predicate(schema);
        LogicContextContainer env = new LogicContextContainer();
        env.put("d", 1);
        Assertions.assertTrue(predicate.test(env));
        Info.YellowText(TokenCompiler.norm(input));
        Assertions.assertEquals(TokenCompiler.JSON2Plain(json), TokenCompiler.norm(input));

    }
}
