package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;

import java.lang.annotation.Annotation;

/**
 * Created by zoowii on 14/10/30.
 */
public class NotValidateConstrait extends Constrait<Annotation> {
    @Override
    public boolean isValid(Object fieldValue) {
        return true;
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return null;
    }
}
