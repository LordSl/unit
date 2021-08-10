import com.lordsl.unit.common.HandlerModel;
import com.lordsl.unit.common.OpFacade;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestOp {

    @Test
    void cal() {
        List<Class<? extends HandlerModel>> cla = OpFacade.getAllHandlerImpl("com.lordsl.unit.test.example");
        System.out.println(cla.toString());
    }

}
