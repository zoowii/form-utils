package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;

import javax.validation.constraints.DecimalMin;

/**
 * Created by zoowii on 14/10/30.
 */
public class DecimalMinConstrait extends Constrait<DecimalMin> {
    @Override
    public boolean isInValid(Object fieldValue) {
        if (fieldValue == null) {
            return false;
        }
        boolean isError = false;
        if (!(fieldValue instanceof Integer || fieldValue instanceof Long || fieldValue instanceof Float || fieldValue instanceof Double)) {
            isError = true;
        } else {
            try {
                Double fieldDoubleValue = Double.valueOf(fieldValue.toString());
                if (fieldDoubleValue < Double.valueOf(validateAnnotation.value())) {
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
        return validateErrorGenerator.generate(
                validateAnnotation, ValidateHelper.firstNotEmptyString(
                        validateAnnotation.message(),
                        String.format("Field %s must be >= %f",
                                validateErrorGenerator.getFieldName(), Double.valueOf(validateAnnotation.value()))));
    }
}
