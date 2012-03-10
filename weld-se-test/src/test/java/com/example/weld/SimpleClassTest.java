package com.example.weld;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleClassTest {

    @Inject
    SimpleClass simple;

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "archive.jar").addAsResource("META-INF/beans.xml", "META-INF/beans.xml")
                .addPackage("com.example.weld");
    }

    @Test
    public void first_test() {
        Assert.assertNotNull(this.simple);
    }

    @Test
    public void first_second() {
        final String name = "marko";
        Assert.assertEquals("Hello " + name.toUpperCase(), this.simple.sayHello(name));
    }
}
