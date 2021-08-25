import com.alibaba.fastjson.JSON;
import com.lordsl.unit.common.condition.parser.ConditionParser;
import com.lordsl.unit.common.condition.parser.TokenSchema;
import com.lordsl.unit.common.condition.parser.TokenType;
import com.lordsl.unit.common.util.Container;
import com.lordsl.unit.common.util.Info;
import org.junit.jupiter.api.Test;

public class TestParser {

    @Test
    void test() {
        TokenSchema A大于三 = TokenSchema.create()
                .type(TokenType.Condition)
                .addSub(TokenSchema.create()
                        .type(TokenType.Num)
                        .addSub(TokenSchema.create()
                                .val("A")
                                .type(TokenType.Var)))
                .addSub(TokenSchema.create()
                        .val(">")
                        .type(TokenType.Compare))
                .addSub(TokenSchema.create()
                        .val("2")
                        .type(TokenType.Num));


        TokenSchema 三大于二 = TokenSchema.create()
                .type(TokenType.Condition)
                .addSub(TokenSchema.create()
                        .val("3")
                        .type(TokenType.Num))
                .addSub(TokenSchema.create()
                        .val(">")
                        .type(TokenType.Compare))
                .addSub(TokenSchema.create()
                        .val("2")
                        .type(TokenType.Num));

        TokenSchema schema = TokenSchema.create()
                .type(TokenType.Condition)
                .addSub(A大于三)
                .addSub(TokenSchema.create().val("&").type(TokenType.Logic))
                .addSub(三大于二);

        Info.YellowText(JSON.toJSON(schema).toString());

        ConditionParser parser = ConditionParser.parse(schema);
        Container context = new Container();
        context.put("A", 1);
        Info.BlueInfo(parser.apply(context).toString());
    }
}
