package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;

import javax.validation.constraints.Digits;

/**
 * Created by zoowii on 14/10/30.
 */
public class DigitsConstrait extends Constrait<Digits> {
    @Override
    public boolean isInValid(Object fieldValue) {
        if (fieldValue == null) {
            return false;
        }
        throw new UnsupportedOperationException();
//        boolean isError = false;
//        try {
//            DigitsValidatorForNumber constraint = new DigitsValidatorForNumber();
//            constraint.initialize(validateAnnotation);
//            if (!constraint.isValid(Double.valueOf(fieldValue.toString()), null)) {
//                isError = true;
//            }
//        } catch (Exception e) {
//            isError = true;
//        }
//        return isError;
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return validateErrorGenerator.generate(validateAnnotation, ValidateHelper.firstNotEmptyString(validateAnnotation.message(), String.format("Field %s must be as digits format (%d, %d)", validateErrorGenerator.getFieldName(), validateAnnotation.integer(), validateAnnotation.fraction())));
    }
}
