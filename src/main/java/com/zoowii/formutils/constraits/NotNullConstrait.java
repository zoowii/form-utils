package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;

import javax.validation.constraints.NotNull;

/**
 * Created by zoowii on 14/10/30.
 */
public class NotNullConstrait extends Constrait<NotNull> {
    @Override
    public boolean isInValid(Object fieldValue) {
        return fieldValue == null;
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return validateErrorGenerator.generate(validateAnnotation, ValidateHelper.firstNotEmptyString(
                validateAnnotation.message(), String.format("Field %s can't be null", validateErrorGenerator.getFieldName())));
    }
}
