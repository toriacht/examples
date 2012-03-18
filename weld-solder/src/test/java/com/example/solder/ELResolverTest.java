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
 * Expression language 1.0 feature demonstration
 * 
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
		// check if expression resolver is not null
		Assert.assertNotNull("Expression resolver must be not null", this.expression);
	}

	/*
	 * 
	 * 
	 */
	@Test
	public void resolveExpressionTest() {
		// check if expression is evaluating
		Assert.assertNotNull(this.expression.evaluateValueExpression("${configured}"));
		// evaluation of ${configured} expression
		Assert.assertEquals("RETURN VALUE", this.expression.evaluateValueExpression("${configured}"));
		// logical expression
		Assert.assertTrue((Boolean) this.expression.evaluateValueExpression("${configured == 'RETURN VALUE'}"));
		// not an expression
		Assert.assertEquals("configured", this.expression.evaluateValueExpression("configured"));
		// string and expression concatenation
		Assert.assertEquals("SEE RETURN VALUE", this.expression.evaluateValueExpression("SEE ${configured}"));
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
		// arithmetic operation
		Assert.assertEquals(3L, this.expression.evaluateValueExpression("${map.NUM_1 + map.NUM_2}"));
		// logical expression using maps
		Assert.assertTrue((Boolean) this.expression.evaluateValueExpression("${map.NUM_1 < map.NUM_2}"));
	}

	/*
	 *
	 * 
	 */
	@Test
	public void resolveNamedExpressionTest() {
		// resolution of beans annotated with @Named
		Assert.assertNotNull(this.expression.evaluateValueExpression("${simpleClass}"));
		// accessing java bean property
		Assert.assertEquals("Homer",
				this.expression.evaluateValueExpression("${simpleClass.name}"));
		// accessing java bean property + string concatenation
		Assert.assertEquals("Homer Simpson",
				this.expression.evaluateValueExpression("${simpleClass.name} ${simpleClass.lastName}"));
		// accessing java bean property + arithmetic operation
		Assert.assertEquals(3L,
				this.expression.evaluateValueExpression("${simpleClass.first + simpleClass.second}"));
		// accessing java bean property + string concatenation
		Assert.assertEquals("1+2",
				this.expression.evaluateValueExpression("${simpleClass.first}+${simpleClass.second}"));
	}

	/*
	 * The EL supports both immediate and deferred evaluation of expressions. 
	 * Immediate evaluation means that the expression is evaluated and the result returned as soon as the page is first rendered. 
	 * Deferred evaluation means that the technology using the expression language can use its own machinery to evaluate the 
	 * expression sometime later during the page’s lifecycle, whenever it is appropriate to do so.
	 * 
	 * Those expressions that are evaluated immediately use the ${} syntax. 
	 * Expressions whose evaluation is deferred use the #{} syntax.
	 *  
	 */
	@Test
	public void resolveDeferredExpressionTest() {
		Assert.assertNotNull(this.expression.evaluateValueExpression("#{configured}"));
		// deferred evaluation of #{configured} expression
		Assert.assertEquals("RETURN VALUE", this.expression.evaluateValueExpression("#{configured}"));
		// deferred logical expression
		Assert.assertTrue((Boolean) this.expression.evaluateValueExpression("#{configured == 'RETURN VALUE'}"));
	}


}
