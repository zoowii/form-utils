package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;

import javax.validation.constraints.Max;

/**
 * Created by zoowii on 14/10/30.
 */
public class MaxConstrait extends Constrait<Max> {
    @Override
    public boolean isInValid(Object fieldValue) {
        if (fieldValue == null) {
            return false;
        }
        boolean isError = false;
        if (!(fieldValue instanceof Integer || fieldValue instanceof Long)) {
            isError = true;
        } else {
            try {
                long fieldLongValue = Long.valueOf(fieldValue.toString());
                if (fieldLongValue > validateAnnotation.value()) {
                    isError = true;
                }
            } catch (Exception e) {
                isError = true;
            }
        }
        return isError;
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return validateErrorGenerator.generate(validateAnnotation, ValidateHelper.firstNotEmptyString(validateAnnotation.message(), String.format("Field %s must be <= %d", validateErrorGenerator.getFieldName(), validateAnnotation.value())));
    }
}
