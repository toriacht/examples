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

        final ValidatorFactory factory = configuration.addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory();

        return factory.getValidator();

    }
}
