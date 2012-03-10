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

package com.test.weld.extension;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.weld.ClassA;
import com.test.weld.ClassB;

/**
 * 
 * @author marko
 * 
 */
public class WeldExtension implements Extension {

	private static final Logger LOG = LoggerFactory.getLogger(WeldExtension.class);

	/**
	 * after bean discovery
	 * 
	 * @param event
	 * @param beanManager
	 */

	void afterBeanDiscovery(@Observes final AfterBeanDiscovery event, final BeanManager beanManager) {
		LOG.info("[afterBeanDiscovery       ] ClassA beans : {}", beanManager.getBeans(ClassA.class).size());
		LOG.info("[afterBeanDiscovery       ] ClassB beans : {}", beanManager.getBeans(ClassB.class).size());
		LOG.info("[afterBeanDiscovery       ] Object beans : {}", beanManager.getBeans(Object.class).size());
	}

	/**
	 * after bean validation
	 * 
	 * @param event
	 * @param beanManager
	 */

	void afterDeploymentValidation(@Observes final AfterDeploymentValidation event, final BeanManager beanManager) {
		LOG.info("[afterDeploymentValidation] ClassA beans : {}", beanManager.getBeans(ClassA.class).size());
		LOG.info("[afterDeploymentValidation] ClassB beans : {}", beanManager.getBeans(ClassB.class).size());
		LOG.info("[afterDeploymentValidation] Object beans : {}", beanManager.getBeans(Object.class).size());
	}
}
