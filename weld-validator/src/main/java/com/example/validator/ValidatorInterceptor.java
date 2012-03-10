package com.example.validator;

import java.util.Set;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.Validator;

import org.slf4j.Logger;

/**
 * Validate result interceptor 
 * @author marko
 *
 */

@Interceptor
@ProducerValidatorBinding
public class ValidatorInterceptor {
	
	@Inject Logger logger;
	@Inject Validator validator;
	/**
	 * 
	 * @param invocationContext
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object manage(InvocationContext invocationContext) throws Exception {
		logger.debug("interceptor called {}", invocationContext);
		InjectionPoint injectionPoint = (InjectionPoint) invocationContext.getParameters()[0];
		Object result = invocationContext.proceed();
		
		this.injectionPointValidator(injectionPoint, result);
		
		return result;
	}
	/**
	 * 
	 * @param injectionPoint
	 * @param value
	 * @return
	 */
	public boolean injectionPointValidator(InjectionPoint injectionPoint, Object value){
		String name 	= injectionPoint.getMember().getName();
		Class<?> clazz 	= injectionPoint.getBean().getBeanClass();
		
		logger.debug("validating injection point [{}] in class [{}]",name,clazz);
		
		Set<?> violations =  validator.validateValue(clazz, name, value);
		
		if (violations.size() > 0){
			logger.warn("[{}]: violation(s) detected: {}", name, violations);
			return true;
		} else {
			logger.debug("[{}]: no violation(s) detected!",name);
			return false;
		}
	}

}
