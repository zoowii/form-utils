package com.zoowii.formutils.constraits;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;

import javax.validation.constraints.Past;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zoowii on 14/10/30.
 */
public class PastConstrait extends Constrait<Past> {
    @Override
    public boolean isInValid(Object fieldValue) {
        if (fieldValue == null) {
            return false;
        }
        boolean isError = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            Date fieldDateValue;
            if (fieldValue instanceof Date) {
                fieldDateValue = (Date) fieldValue;
            } else {
                fieldDateValue = sdf.parse(fieldValue.toString());
            }
            if (fieldDateValue.after(new Date())) {
                isError = true;
            }
        } catch (Exception e) {
            isError = true;
        }
        return isError;
    }

    @Override
    public ValidateError createValidateError(ValidateErrorGenerator validateErrorGenerator) {
        return validateErrorGenerator.generate(validateAnnotation, ValidateHelper.firstNotEmptyString(validateAnnotation.message(), String.format("Field %s must be date before now", validateErrorGenerator.getFieldName())));
    }
}
