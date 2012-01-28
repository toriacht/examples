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
 * 
 *
 */
public class WeldExtension implements Extension {
	
	private static final Logger LOG = LoggerFactory.getLogger(WeldExtension.class);
	
	/**
	 * 
	 * @param event
	 * @param beanManager
	 */
	
	void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager beanManager) { 
		LOG.info("[afterBeanDiscovery       ] ClassA beans : {}", beanManager.getBeans(ClassA.class).size());
		LOG.info("[afterBeanDiscovery       ] ClassB beans : {}", beanManager.getBeans(ClassB.class).size());
		LOG.info("[afterBeanDiscovery       ] Object beans : {}", beanManager.getBeans(Object.class).size());
	}
	
	/**
	 * 
	 * @param event
	 * @param beanManager
	 */
	
	void afterDeploymentValidation(@Observes AfterDeploymentValidation event, BeanManager beanManager) {
		LOG.info("[afterDeploymentValidation] ClassA beans : {}", beanManager.getBeans(ClassA.class).size());
		LOG.info("[afterDeploymentValidation] ClassB beans : {}", beanManager.getBeans(ClassB.class).size());
		LOG.info("[afterDeploymentValidation] Object beans : {}", beanManager.getBeans(Object.class).size());	
	}
}
