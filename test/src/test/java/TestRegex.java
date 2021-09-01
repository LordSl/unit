import com.lordsl.unit.compiler.Token;
import com.lordsl.unit.util.Info;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

    public static void test(String regex, String test) {
        Info.GreenLog(String.format("for regex 「%s」 input 「%s」", regex, test));
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(test);
        boolean isMatch = m.matches();
        Info.PurpleAlert((String.format("match or not: %s", isMatch)));
        if (isMatch)
            Info.YellowText(String.format("match res: %s", m.group(0)));
        Info.WhiteLine();
    }

    @Test
    void test() {
        test(Token.Regex.Var, "a3");
        test(Token.Regex.Var, "qwe_8");
        test(Token.Regex.Num, "-3.230");
        test(Token.Regex.Num, "-298");
        test(Token.Regex.Num, "0");
        test(Token.Regex.Num, "-2.asd3");
        test(Token.Regex.Cal, "+");
        test(Token.Regex.Cal, "#");
        test(Token.Regex.Compare, ">");
        test(Token.Regex.Compare, ">>");
        test(Token.Regex.Logic, "&");
        test(Token.Regex.Logic, "@");
        test(Token.Regex.LP, "(");
        test(Token.Regex.RP, ")");
        test(Token.Regex.Init, ":=");
    }
}
