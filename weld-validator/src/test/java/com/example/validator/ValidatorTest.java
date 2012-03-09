package com.example.validator;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import junit.framework.Assert;

/**
 * 
 * @author marko
 *
 */

@RunWith(Arquillian.class)
public class ValidatorTest {
	
	@Inject
	private SimpleBean simpleBean;

	@Deployment
	public static Archive<?> createTestArchive() {
      return ShrinkWrap.create(JavaArchive.class, "archive.jar").
    		  addAsResource("META-INF/beans.xml", "META-INF/beans.xml").
    		  addPackage("com.example.validator");
	}
	
	@Test
	public void test(){
		Assert.assertNotNull(this.simpleBean);
	}
}
