package com.zoowii.formutils;

import java.lang.annotation.Annotation;

/**
 * Created by zoowii on 14/10/30.
 */
public abstract class Constrait<T extends Annotation> {
    protected T validateAnnotation;

    public void initialize(Annotation validateAnnotation) {
        this.validateAnnotation = (T) validateAnnotation;
    }

    public boolean isValid(Object fieldValue) {
        return !isInValid(fieldValue);
    }

    public boolean isInValid(Object fieldValue) {
        return !isValid(fieldValue);
    }

    public abstract ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator);
}
