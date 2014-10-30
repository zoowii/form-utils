package com.zoowii.formutils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zoowii on 14/10/30.
 */
public class BindingResult {
    private Object target;
    private Class<?> model;
    private List<ValidateError> validateErrors = new ArrayList<>();

    public void addError(ValidateError validateError) {
        validateErrors.add(validateError);
    }

    public Iterator<ValidateError> getErrors() {
        return validateErrors.iterator();
    }

    public int getErrorCount() {
        return validateErrors.size();
    }

    public boolean hasErrors() {
        return !validateErrors.isEmpty();
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Class<?> getModel() {
        return model;
    }

    public void setModel(Class<?> model) {
        this.model = model;
    }
}
