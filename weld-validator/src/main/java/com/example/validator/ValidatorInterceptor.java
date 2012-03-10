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
 * Validator interceptor - intercepts and validates if produced value
 * obeys validation constraints 
 *  
 * @author marko
 *
 */

@Interceptor
@ValidatorBinding
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
		InjectionPoint injectionPoint = findInjectionPoint(invocationContext);
		
		Object result = invocationContext.proceed();
		// do something smart with the result
		this.injectionPointValidator(injectionPoint, result);
		
		return result;
	}
	/**
	 * 
	 * @param invocationContext
	 * @return
	 */
	private InjectionPoint findInjectionPoint(InvocationContext invocationContext) {
		for (Object parameter : invocationContext.getParameters()){
			if (parameter instanceof InjectionPoint){
				logger.debug("found injection point parameter {}",parameter);
				return (InjectionPoint) parameter;
			}
		}
		
		logger.debug("didn't find injection point parameter, returning null");
		return null;
	}
	/**
	 * 
	 * @param injectionPoint
	 * @param value
	 * @return
	 */
	public boolean injectionPointValidator(InjectionPoint injectionPoint, Object value){
		
		if (injectionPoint == null){
			logger.debug("can't validate null injection point");
			return true;
		}
		
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
