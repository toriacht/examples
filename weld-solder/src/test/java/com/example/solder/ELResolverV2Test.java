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
package com.example.solder;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.solder.el.Expressions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Expression language 2.2 (JSR-341) feature demonstration
 * 
 * <ul>
 * <li>method calls</li>
 * <li>parameters</li>
 * </ul>
 * 
 * @see http://jcp.org/en/jsr/detail?id=341
 * @author marko
 * 
 */
@RunWith(Arquillian.class)
public class ELResolverV2Test {
	@Inject
	Expressions expression;

	/*
	 * 
	 * 
	 */
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class)
				.addAsManifestResource("META-INF/beans.xml")
				.addPackage(ConfigELResolver.class.getPackage().getName())
				.addPackage(Expressions.class.getPackage().getName());
	}

	/*
	 * 
	 *  
	 */
	@Test
	public void expressionsNotNullTest() {
		Assert.assertNotNull("Expression resolver must be not null", this.expression);
	}

	/*
	 * 
	 * 
	 */
	@Test
	public void resolveExpressionTest() {
		Assert.assertNotNull(this.expression.evaluateValueExpression("${configured}"));
		// a method call from within EL expression
		Assert.assertEquals("RETURN VALUE".toLowerCase(),
				this.expression.evaluateValueExpression("${configured.toLowerCase()}"));
	}

	/*
	 * 
	 * 
	 */
	@Test
	public void resolveNamedExpressionTest() {
		Assert.assertEquals("HOMER",
				this.expression.evaluateValueExpression("${simpleClass.toUpperCase('homer')}"));
		// method call without parameters
		Assert.assertEquals(5,
				this.expression.evaluateValueExpression("${simpleClass.name.length()}"));
		// method call with two parameters
		Assert.assertEquals("Simpson Homer",
				this.expression.evaluateValueExpression("${simpleClass.concatInverse(simpleClass.name, simpleClass.lastName)}"));
	}

	/*
	 * using ENUMs  
	 */
	@Test
	public void resolveEnumExpressionTest() {
		// demonstrating ENUM expression
		Assert.assertTrue((Boolean) this.expression.evaluateValueExpression("#{simpleClass.simpleEnumValue == 'SOUTH'}"));
	}
}
