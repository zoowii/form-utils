package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;

import javax.validation.constraints.Size;

/**
 * Created by zoowii on 14/10/30.
 */
public class SizeConstrait extends Constrait<Size> {
    @Override
    public boolean isValid(Object fieldValue) {
        if (fieldValue == null) {
            return true;
        }
        int size = fieldValue.toString().length();
        return size >= validateAnnotation.min() && size <= validateAnnotation.max();
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return validateErrorGenerator.generate(validateAnnotation, ValidateHelper.firstNotEmptyString(validateAnnotation.message(), String.format("Field %s's length must be %d to %d", validateErrorGenerator.getFieldName(), validateAnnotation.min(), validateAnnotation.max())));
    }
}
