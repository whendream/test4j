package org.jtester.json.encoder.array;

import java.io.StringWriter;
import java.util.ArrayList;

import org.jtester.junit.JTester;
import org.junit.Test;
import org.test4j.json.encoder.JSONEncoder;
import org.test4j.json.encoder.array.ObjectArrayEncoder;
import org.test4j.json.helper.JSONFeature;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BooleanArrayEncoderTest implements JTester {
    @Test
    public void testEncode() throws Exception {
        boolean[] values = new boolean[] { true, false, true };

        JSONEncoder encoder = JSONEncoder.get(values.getClass());
        StringWriter writer = new StringWriter();
        encoder.setFeatures(JSONFeature.UnMarkClassFlag);
        encoder.encode(values, writer, new ArrayList<String>());
        String json = writer.toString();
        want.string(json).eqIgnoreSpace("[true,false,true]");
    }

    @Test
    public void testEncode_Boolean() throws Exception {
        Boolean[] values = new Boolean[] { true, null, false };

        JSONEncoder encoder = JSONEncoder.get(values.getClass());
        want.object(encoder).clazIs(ObjectArrayEncoder.class);

        StringWriter writer = new StringWriter();
        encoder.setFeatures(JSONFeature.UnMarkClassFlag);
        encoder.encode(values, writer, new ArrayList<String>());
        String json = writer.toString();
        want.string(json).eqIgnoreSpace("[true,null,false]");
    }
}
