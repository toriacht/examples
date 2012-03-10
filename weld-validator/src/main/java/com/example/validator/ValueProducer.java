package com.example.validator;

import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Validator;

import org.slf4j.Logger;

/**
 * 
 * How to verify injection point before injection example
 * current implementation does not support method level injection 
 * 
 * @author marko
 *
 */

@Singleton
@ProducerValidatorBinding
public class ValueProducer {
	
	
	@Inject Logger logger;
	
	/**
	 * 
	 * @param injectionPoint
	 * @return
	 */
	
	@Produces
	public Integer produceInt(InjectionPoint injectionPoint) {
		
		Object value 	= 8;
		
		
		return (Integer) value;
	}
}
