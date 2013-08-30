package org.jtester.json.decoder.single;

import java.util.HashMap;

import org.jtester.junit.JTester;
import org.junit.Test;
import org.test4j.json.JSON;
import org.test4j.json.decoder.single.ShortDecoder;
import org.test4j.json.helper.JSONFeature;
import org.test4j.json.helper.JSONMap;

public class ShortDecoderTest implements JTester {

    @Test
    public void testDecodeSimpleValue() {
        JSONMap json = new JSONMap() {
            private static final long serialVersionUID = 1L;

            {
                this.putJSON(JSONFeature.ValueFlag, Short.valueOf("23"));
            }
        };
        ShortDecoder decoder = ShortDecoder.toSHORT;
        Short sht = decoder.decode(json, Short.class, new HashMap<String, Object>());
        want.number(sht).isEqualTo(23);
    }

    @Test
    public void testShortArray() {
        String json = "[[2,3],[4,5]]";
        Short[][] o = JSON.toObject(json, short[][].class);
        want.array(o).reflectionEq(new short[][] { { 2, 3 }, { 4, 5 } });
    }
}
