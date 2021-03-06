package org.test4j.module.jmockit;

import mockit.Mocked;

import org.junit.Test;
import org.test4j.fortest.formock.SomeInterface;
import org.test4j.fortest.formock.SpringBeanService;
import org.test4j.fortest.formock.SpringBeanService.SpringBeanServiceImpl1;
import org.test4j.junit.Test4J;
import org.test4j.module.inject.annotations.Inject;

public class MockTest_ByName extends Test4J {

    private final SpringBeanService springBeanService = new SpringBeanServiceImpl1();

    @Inject(targets = "springBeanService", properties = "dependency1")
    @Mocked
    private SomeInterface           someInterface1;

    @Inject(targets = "springBeanService", properties = "dependency2")
    @Mocked
    private SomeInterface           someInterface2;

    @Test
    public void testMock_ByName() {
        SomeInterface intf1 = springBeanService.getDependency1();
        want.object(intf1).not(the.object().same(someInterface1));
        want.object(springBeanService.getDependency2()).not(the.object().same(someInterface2));
    }
}
