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
 * @author marko
 *
 */
@RunWith(Arquillian.class)
public class ELResolverTest {

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
	public void expressionsNotNullTest(){
		Assert.assertNotNull("Expression resolver must be not null", this.expression);
	}

	/*
	 * 
	 * 
	 */
	@Test
	public void resolveExpressionTest() {
		Assert.assertNotNull(this.expression.evaluateValueExpression("${configured}"));
		// demonstrating evaluation of ${configured} expression
		Assert.assertEquals("RETURN VALUE", this.expression.evaluateValueExpression("${configured}"));
		// demonstrating logical expression
		Assert.assertTrue((Boolean) this.expression.evaluateValueExpression("${configured == 'RETURN VALUE'}"));
	}

	/*
	 * 
	 * 
	 */
	@Test
	public void resolveMapExpressionTest() {
		// accessing map using ${map['KEY']}"
		Assert.assertNotNull(this.expression.evaluateValueExpression("${map['KEY']}"));
		Assert.assertEquals("value", this.expression.evaluateValueExpression("${map['KEY']}"));
		// accessing map using ${map.KEY}"
		Assert.assertEquals("value", this.expression.evaluateValueExpression("${map.KEY}"));
	}

	/*
	 * 
	 * 
	 */
	@Test
	public void resolveOperationsMapExpressionTest() {
		// demonstrating arithmetic operation
		Assert.assertEquals(3L, this.expression.evaluateValueExpression("${map.NUM_1 + map.NUM_2}"));
		// demonstrating logical expression using maps
		Assert.assertTrue((Boolean) this.expression.evaluateValueExpression("${map.NUM_1 < map.NUM_2}"));
	}

	/*
	 *
	 * 
	 */
	@Test
	public void resolveNamedExpressionTest() {
		// demonstrating resolution of beans annotated with @Named
		Assert.assertNotNull(this.expression.evaluateValueExpression("${simpleClass}"));
		// accessing java bean property
		Assert.assertEquals("Marko",
				this.expression.evaluateValueExpression("${simpleClass.name}"));
		// accessing java bean property + string concatenation
		Assert.assertEquals("Marko Milenkovic",
				this.expression.evaluateValueExpression("${simpleClass.name} ${simpleClass.lastName}"));
		// accessing java bean property + arithmetic operation
		Assert.assertEquals(3L,
				this.expression.evaluateValueExpression("${simpleClass.first + simpleClass.second}"));
		// accessing java bean property + string concatenation
		Assert.assertEquals("1+2",
				this.expression.evaluateValueExpression("${simpleClass.first}+${simpleClass.second}"));
	}
}
