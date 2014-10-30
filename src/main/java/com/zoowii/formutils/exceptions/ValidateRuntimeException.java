package com.zoowii.formutils.exceptions;

/**
 * Created by zoowii on 14/10/30.
 */
public class ValidateRuntimeException extends RuntimeException {
    public ValidateRuntimeException() {
        super();
    }

    public ValidateRuntimeException(String msg) {
        super(msg);
    }

    public ValidateRuntimeException(Exception e) {
        super(e);
    }
}
