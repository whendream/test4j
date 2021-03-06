package org.test4j.json.encoder.object.spec;

import java.lang.reflect.Method;

import org.test4j.json.JSON;
import org.test4j.json.encoder.EncoderTest;
import org.test4j.json.helper.JSONFeature;
import org.testng.annotations.Test;

@Test(groups = "json")
public class MethodEncoderTest extends EncoderTest {

    @Test
    public void testEncodeSingleValue() throws Exception {
        Method m = MethodJsonDemo.class.getDeclaredMethod("demo2Para", String.class, Integer.class);
        want.object(m).notNull();
        MethodEncoder encoder = MethodEncoder.instance;
        this.setUnmarkFeature(encoder);
        encoder.encode(m, writer, references);

        String json = writer.toString();
        want.string(json)
                .isEqualTo(
                        "{methodName:'demo2Para',declaredBy:'org.test4j.json.encoder.object.spec.MethodJsonDemo',paraType:['java.lang.String','java.lang.Integer']}");
    }

    @Test
    public void testEncodeMethod() {
        MethodJsonDemo target = new MethodJsonDemo();
        String json = JSON.toJSON(target, JSONFeature.UnMarkClassFlag, JSONFeature.UseSingleQuote,
                JSONFeature.SkipNullValue);
        want.string(json)
                .isEqualTo(
                        "{method:{methodName:'demo2Para',declaredBy:'org.test4j.json.encoder.object.spec.MethodJsonDemo',paraType:['java.lang.String','java.lang.Integer']},name:'MethodJsonDemo'}");
    }

}

@SuppressWarnings("unused")
class MethodJsonDemo {
    private Method       method;

    private final String name = "MethodJsonDemo";

    public MethodJsonDemo() {
        try {
            this.method = MethodJsonDemo.class.getDeclaredMethod("demo2Para", String.class, Integer.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void demo2Para(String para1, Integer v1) {
    }
}
