package com.example;

import java.util.Set;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.weld.HasAnObserver;
import com.example.weld.HasSomeOtherObserver;
import com.example.weld.SimpleClass;
/**
 * How to find methods that @Observes for specific events
 * https://community.jboss.org/thread/180217?tstart=0
 * 
 * @author marko
 *
 */
@RunWith(Arquillian.class)
public class TestClass {
	
	@Inject BeanManager manager;
	@Deployment	
	public static Archive<?> createTestArchive() {
      return ShrinkWrap.create(JavaArchive.class, "archive.jar").
    		  addAsResource("META-INF/beans.xml", "META-INF/beans.xml").
    		  addPackage("com.example.weld");
	}
	
	@Test	
	public void manager_not_null(){
		Assert.assertNotNull(this.manager);
	}
	
	
	@Test	
	public void test_resolver_simple_class(){ 
		
		Set<ObserverMethod<? super SimpleClass>> result =  this.manager.resolveObserverMethods ( new SimpleClass() );
		
		Assert.assertEquals(1, result.size());
		
		for (ObserverMethod<? super SimpleClass>  i : result ){
			Assert.assertEquals(HasAnObserver.class, i.getBeanClass());
		}
	}
	
	@Test	
	public void test_resolver_simple_integer(){ 
		
		Set<ObserverMethod<? super Integer>> result =  this.manager.resolveObserverMethods ( new Integer(1) );
		
		Assert.assertEquals(1, result.size());
		
		for (ObserverMethod<? super Integer>  i : result ){
			Assert.assertEquals(HasSomeOtherObserver.class, i.getBeanClass());
		}
	}

}
