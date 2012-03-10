package com.example.validator;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author marko
 * 
 */
@Singleton
public class LoggerProducer {
    @Produces
    public Logger createLogger(final InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getSimpleName());
    }
}
