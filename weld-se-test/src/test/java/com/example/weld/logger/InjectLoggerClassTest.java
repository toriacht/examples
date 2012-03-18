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
package com.example.weld.logger;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.solder.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.weld.logger.InjectLoggerClass;

/**
 * testing seam solder logging
 * 
 * @author marko
 * 
 */
@RunWith(Arquillian.class)
public class InjectLoggerClassTest {

	@Inject
	InjectLoggerClass injectLoggerClass;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class)
				.addAsResource("META-INF/beans.xml", "META-INF/beans.xml")
				.addPackage(InjectLoggerClass.class.getPackage().getName())
				.addPackage(Logger.class.getPackage().getName());

	}

	@Test
	public void notNullTest() {
		Assert.assertNotNull(this.injectLoggerClass);

	}

	@Test
	public void testCategory() {
		Assert.assertEquals("TEST_CATEGORY", this.injectLoggerClass.getLogger().getName());
	}

}
