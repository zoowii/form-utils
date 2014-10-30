package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;
import com.zoowii.formutils.annotations.Email;

/**
 * Created by zoowii on 14/10/30.
 */
public class EmailConstrait extends Constrait<Email> {
    @Override
    public boolean isValid(Object fieldValue) {
        if (fieldValue == null) {
            return true;
        }
        return fieldValue.toString().matches("^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$");
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return validateErrorGenerator.generate(validateAnnotation, ValidateHelper.firstNotEmptyString(validateAnnotation.message(), String.format("Field %s must be email format", validateErrorGenerator.getFieldName())));
    }
}
