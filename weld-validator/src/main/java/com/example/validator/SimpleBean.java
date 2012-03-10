package com.example.validator;

import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 
 * @author marko
 * 
 */
public class SimpleBean {

    @Inject
    @Min(10)
    private Integer integer;

    @Inject
    @Min(5)
    @Max(10)
    private Integer validInteger;

    public Integer getInteger() {
        return this.integer;
    }

    public void setInteger(final Integer integer) {
        this.integer = integer;
    }

    public Integer getValidInteger() {
        return this.validInteger;
    }

    public void setValidInteger(final Integer validInteger) {
        this.validInteger = validInteger;
    }
}
