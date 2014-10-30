package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;

import javax.validation.constraints.Null;

/**
 * Created by zoowii on 14/10/30.
 */
public class NullConstrait extends Constrait<Null> {
    @Override
    public boolean isInValid(Object fieldValue) {
        return fieldValue != null;
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return validateErrorGenerator.generate(validateAnnotation, ValidateHelper.firstNotEmptyString(
                validateAnnotation.message(), String.format("Field %s must be null", validateErrorGenerator.getFieldName())));
    }
}
