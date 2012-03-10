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
 * Validator interceptor - intercepts and validates if produced value obeys validation constraints
 * 
 * @author marko
 * 
 */

@Interceptor
@ValidatorBinding
public class ValidatorInterceptor {

    @Inject
    Logger logger;
    @Inject
    Validator validator;

    /**
     * 
     * @param invocationContext
     * @return
     * @throws Exception
     */
    @AroundInvoke
    public Object manage(final InvocationContext invocationContext) throws Exception {
        this.logger.debug("interceptor called {}", invocationContext);
        final InjectionPoint injectionPoint = this.findInjectionPoint(invocationContext);

        final Object result = invocationContext.proceed();
        // do something smart with the result
        this.injectionPointValidator(injectionPoint, result);

        return result;
    }

    /**
     * 
     * @param invocationContext
     * @return
     */
    private InjectionPoint findInjectionPoint(final InvocationContext invocationContext) {
        for (final Object parameter : invocationContext.getParameters()) {
            if (parameter instanceof InjectionPoint) {
                this.logger.debug("found injection point parameter {}", parameter);
                return (InjectionPoint) parameter;
            }
        }

        this.logger.debug("didn't find injection point parameter, returning null");
        return null;
    }

    /**
     * 
     * @param injectionPoint
     * @param value
     * @return
     */
    public boolean injectionPointValidator(final InjectionPoint injectionPoint, final Object value) {

        if (injectionPoint == null) {
            this.logger.debug("can't validate null injection point");
            return true;
        }

        final String name = injectionPoint.getMember().getName();
        final Class<?> clazz = injectionPoint.getBean().getBeanClass();

        this.logger.debug("validating injection point [{}] in class [{}]", name, clazz);

        final Set<?> violations = this.validator.validateValue(clazz, name, value);

        if (violations.size() > 0) {
            this.logger.warn("[{}]: violation(s) detected: {}", name, violations);
            return true;
        } else {
            this.logger.debug("[{}]: no violation(s) detected!", name);
            return false;
        }
    }

}
