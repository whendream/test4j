package org.test4j.tools.commons;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.test4j.fortest.beans.Manager;
import org.test4j.json.JSON;
import org.test4j.json.helper.JSONFeature;
import org.test4j.junit.Test4J;

public class JsonHelperTest_JsonString extends Test4J {
    @Test
    public void fromJson() {
        String filename = "classpath:org/test4j/tools/commons/manager.json";
        Manager manager = JSONHelper.fromJsonFile(Manager.class, filename);
        want.object(manager).propertyEq("name", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
        want.date(manager.getDate()).isYear(2009).isMonth("08").isHour(16);
    }

    @Test
    public void testFromJson() {
        String json = "{\"name\": \"Banana\",\"id\": 123,\"price\": 23.0}";
        Product product = JSON.toObject(json, Product.class);
        want.object(product).propertyEq("name", "Banana").propertyEq("id", 123);
    }

    public static class Product {
        String name;
        int    id;
        double price;

        public Product() {
            this.name = "myname";
            this.id = 100;
            this.price = 1333.00d;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    public void testJsonPojo() {
        Product p = new Product();
        String jsonString = JSON.toJSON(p, JSONFeature.UnMarkClassFlag);
        want.string(jsonString).isEqualTo("{name:\"myname\",id:100,price:1333}");
    }

    @Test
    public void testJsonMap() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("id", 123);
        map.put("name", "张三");

        String text = JSON.toJSON(map, JSONFeature.UnMarkClassFlag);
        want.string(text).isEqualTo("{\"id\":123,\"name\":\"张三\"}");
    }
}
