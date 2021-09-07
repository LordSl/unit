import com.lordsl.unit.util.Container;
import com.lordsl.unit.util.DebugContainer;
import com.lordsl.unit.util.Info;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class TestUtil {

    @Test
    void testContainer() {
        Container container = Container.builder()
                .key("1").val(new BigDecimal("1231241.3213"))
                .key("2").val(Arrays.asList("a", "b", "c"))
                .build();
        Info.BlueInfo(container);
        container.getBuilder()
                .key("haha").val("wtf")
                .key("hi").val("aha")
                .build();
        Info.BlueInfo(container);
        DebugContainer debugContainer = new DebugContainer();
        debugContainer.getBuilder()
                .key("a1").val("b1")
                .key("a2").val("b2")
                .build();
        Integer val = debugContainer.fetch("a1", Integer.class);
        Info.BlueInfo(debugContainer);
    }
}
