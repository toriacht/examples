/*
 * Copyright (c) 2012 Marko Milenkovic
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */

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

	@Inject
	BeanManager manager;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "archive.jar").addAsResource("META-INF/beans.xml", "META-INF/beans.xml")
				.addPackage("com.example.weld");
	}

	@Test
	public void manager_not_null() {
		Assert.assertNotNull(this.manager);
	}

	@Test
	public void test_resolver_simple_class() {

		final Set<ObserverMethod<? super SimpleClass>> result = this.manager.resolveObserverMethods(new SimpleClass());

		Assert.assertEquals(1, result.size());

		for (final ObserverMethod<? super SimpleClass> i : result) {
			Assert.assertEquals(HasAnObserver.class, i.getBeanClass());
		}
	}

	@Test
	public void test_resolver_simple_integer() {

		final Set<ObserverMethod<? super Integer>> result = this.manager.resolveObserverMethods(new Integer(1));

		Assert.assertEquals(1, result.size());

		for (final ObserverMethod<? super Integer> i : result) {
			Assert.assertEquals(HasSomeOtherObserver.class, i.getBeanClass());
		}
	}

}
