package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;

import javax.validation.constraints.Pattern;

/**
 * Created by zoowii on 14/10/30.
 */
public class PatternConstrait extends Constrait<Pattern> {
    @Override
    public boolean isValid(Object fieldValue) {
        if (fieldValue == null) {
            return true;
        }
        return fieldValue.toString().matches(validateAnnotation.regexp());
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return validateErrorGenerator.generate(validateAnnotation, ValidateHelper.firstNotEmptyString(validateAnnotation.message(), String.format("Field %s must match regex %s", validateErrorGenerator.getFieldName(), validateAnnotation.regexp())));
    }
}
