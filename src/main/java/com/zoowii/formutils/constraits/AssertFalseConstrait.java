package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;
import org.apache.commons.lang3.BooleanUtils;

import javax.validation.constraints.AssertFalse;

/**
 * Created by zoowii on 14/10/30.
 */
public class AssertFalseConstrait extends Constrait<AssertFalse> {
    @Override
    public boolean isValid(Object fieldValue) {
        if (fieldValue == null) {
            return true;
        }
        return fieldValue instanceof Boolean && BooleanUtils.isFalse((Boolean) fieldValue);
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return validateErrorGenerator.generate(validateAnnotation,
                ValidateHelper.firstNotEmptyString(validateAnnotation.message(),
                        String.format("Field %s must be false", validateErrorGenerator.getFieldName())));
    }
}
