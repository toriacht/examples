package com.example.validator;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerProducer {
	@Produces Logger createLogger(final InjectionPoint injectionPoint){
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getSimpleName());
	}
}
