package com.example.validator;

import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.validation.Validator;

import org.slf4j.Logger;


public class Producers {
	
	@Inject Validator validator;
	@Inject Logger logger;
	
	
	@Produces
	public Integer produceInt(InjectionPoint injectionPoint) {
		Object value = 8;
		String name = injectionPoint.getMember().getName();
		Class<?> clazz = injectionPoint.getBean().getBeanClass();
		
		logger.debug("validating injection point [{}] in class [{}]",name,clazz);
		
		Set violations =  validator.validateValue(clazz, name, value);
		
		
		if (violations.size() > 0){
			logger.warn("violation(s) detected: {}", violations);
		} else {
			logger.debug("no violation(s) detected");
		}
		
		return 9;
	}
	
	
	

}
