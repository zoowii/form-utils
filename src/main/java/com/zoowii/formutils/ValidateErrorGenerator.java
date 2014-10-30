package com.zoowii.formutils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by zoowii on 14/10/30.
 */
public class ValidateErrorGenerator {
    private Field field;
    private Object fieldValue;

    public Field getField() {
        return field;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public String getFieldName() {
        return field != null ? field.getName() : null;
    }

    public ValidateErrorGenerator(Field field, Object fieldValue) {
        this.field = field;
        this.fieldValue = fieldValue;
    }

    public ValidateError generate(Annotation validateAnnotation, String msg) {
        return new ValidateError(field, fieldValue, validateAnnotation, msg);
    }
}
