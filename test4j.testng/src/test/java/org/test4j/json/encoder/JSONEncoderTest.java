package org.test4j.json.encoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;

import org.test4j.json.JSON;
import org.test4j.json.encoder.beans.test.GenicBean;
import org.test4j.json.encoder.beans.test.TestedIntf;
import org.test4j.json.encoder.beans.test.User;
import org.test4j.json.helper.JSONFeature;
import org.test4j.testng.Test4J;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Test(groups = { "test4j", "json" })
public class JSONEncoderTest extends Test4J {

    @Test(dataProvider = "objects")
    public void testEncode(Object obj, String expected) {
        String json = JSON.toJSON(obj, JSONFeature.UseSingleQuote);
        want.string(json).contains(expected);
    }

    @DataProvider
    public Object[][] objects() {
        return new Object[][] {
                { User.newInstance(12, "darui.wu"), "#class:'org.test4j.json.encoder.beans.test.User@" },// <br>
                { new int[] { 1, 2, 3 }, "#class:'int[]@" },// <br>
                { new HashMap(), "#class:'map@" },// <br>
                { new ArrayList(), "#class:'list@" } // <br>
        };
    }

    @Test(description = "对象循环引用，并且不输出class的情况")
    public void testRefObject() {
        GenicBean bean = new GenicBean();
        bean.setName("genicBean");
        bean.setRefObject(bean);
        String json = JSON.toJSON(bean, JSONFeature.UnMarkClassFlag, JSONFeature.UseSingleQuote);
        want.string(json).eqIgnoreSpace("{name:'genicBean',refObject:null}");
    }

    @Test(description = "对象循环引用，且输出class的情况")
    public void testRefObject_MarkClazz() {
        GenicBean bean = new GenicBean();
        bean.setName("genicBean");
        bean.setRefObject(bean);
        String json = JSON.toJSON(bean, JSONFeature.UseSingleQuote);
        want.string(json).contains("#class:'org.test4j.json.encoder.beans.test.GenicBean@")
                .contains("refObject:{#refer:@");
    }

    @Test(description = "对象是匿名类")
    public void testEncode_ProxyClazz() {
        User user = new User() {
            {
                this.setId(124);
                this.setName("my name");
            }
        };
        String json = JSON.toJSON(user, JSONFeature.UseSingleQuote);
        System.out.println(json);
        want.string(json).contains("#class:'org.test4j.json.encoder.beans.test.User@");
    }

    public void testFilterClass() {
        Object proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] { TestedIntf.class },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });

        GenicBean bean = new GenicBean();
        bean.setName("filter proxy");
        bean.setRefObject(proxy);
        String json = JSON.toJSON(bean, JSONFeature.UseSingleQuote);
        want.string(json).contains("refObject:null");
    }
}
