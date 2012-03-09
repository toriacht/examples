package com.example.validator;

import javax.enterprise.inject.Produces;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

public class ValidatorProducer {
	@Produces
	public Validator produceValidator(){
		HibernateValidatorConfiguration configuration = Validation.byProvider( HibernateValidator.class ).configure();
		ValidatorFactory factory = configuration.addProperty( "hibernate.validator.fail_fast", "true" ).buildValidatorFactory();
		Validator validator = factory.getValidator();
		return validator;
	}
}
