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
    private Logger logger;

    @Inject
    private Validator validator;

    @Inject
    private ValidatorCounter counter;

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
        // handle result somehow
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
            this.logger.debug("skipping null injection point");
            return true;
        }
        this.counter.getProcessed().incrementAndGet();
        final String name = injectionPoint.getMember().getName();
        final Class<?> clazz = injectionPoint.getBean().getBeanClass();

        this.logger.debug("validating injection point: [{}] in class: [{}]", name, clazz);
        this.logger.debug("reflection type: [{}]", injectionPoint.getMember().getClass());
        this.logger.debug("required type: [{}]", injectionPoint.getType());

        final Set<?> violations = this.validator.validateValue(clazz, name, value);

        if (violations.size() > 0) {
            this.counter.getInvalid().incrementAndGet();
            this.logger.warn("[{}]: violation(s) detected: [{}]", name, violations);
            return true;
        } else {

            this.logger.debug("[{}]: no violation(s) detected!", name);
            return false;
        }
    }



}
