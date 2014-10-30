package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;
import com.zoowii.formutils.annotations.NotEmpty;

/**
 * Created by zoowii on 14/10/30.
 */
public class NotEmptyConstrait extends Constrait<NotEmpty> {
    @Override
    public boolean isInValid(Object fieldValue) {
        return fieldValue == null || fieldValue.toString().trim().isEmpty();
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return validateErrorGenerator.generate(validateAnnotation, ValidateHelper.firstNotEmptyString(validateAnnotation.message(), String.format("Field %s can't be empty", validateErrorGenerator.getFieldName())));
    }
}
