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

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

/**
 * @see http://docs.jboss.org/hibernate/validator/4.2/reference/en-US/html/validator-specifics.html TODO: method validator does
 *      not work at the moment
 * 
 * @see http://docs.jboss.org/hibernate/validator/4.2/reference/en-US/html/validator-specifics.html#validator-customoptions-
 *      methodvalidation
 * 
 * @author marko
 * 
 */
@Singleton
public class ValidatorProducer {
    @Produces
    public Validator produceValidator() {

        final HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();

        final ValidatorFactory factory = configuration.addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();

        return factory.getValidator();

    }
}
