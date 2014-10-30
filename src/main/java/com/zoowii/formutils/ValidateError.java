package com.zoowii.formutils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by zoowii on 14/10/30.
 */
public class ValidateError {
    private Field field;
    private String property;
    private Object fieldValue;
    private Annotation errorAnnotation;
    private String errorMessage;

    public ValidateError(Field field, Object fieldValue, Annotation errorAnnotation, String errorMessage) {
        this.field = field;
        this.property = field.getName();
        this.fieldValue = fieldValue;
        this.errorAnnotation = errorAnnotation;
        this.errorMessage = errorMessage;
    }

    public ValidateError() {
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
        if (this.property == null && field != null) {
            this.property = this.field.getName();
        }
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Annotation getErrorAnnotation() {
        return errorAnnotation;
    }

    public void setErrorAnnotation(Annotation errorAnnotation) {
        this.errorAnnotation = errorAnnotation;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
