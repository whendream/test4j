package org.jtester.json.decoder.single;

import org.jtester.junit.JTester;
import org.junit.Test;
import org.test4j.json.JSON;

public class StringDecoderTest implements JTester {

    @Test
    public void testDecodeSimpleValue() {
        String str = JSON.toObject("test string", String.class);
        want.string(str).isEqualTo("test string");
    }
}
