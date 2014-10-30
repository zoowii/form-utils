package com.zoowii.formutils;

import com.zoowii.formutils.Constrait;
import com.zoowii.formutils.ValidateError;
import com.zoowii.formutils.ValidateErrorGenerator;
import com.zoowii.formutils.ValidateHelper;
import com.zoowii.formutils.annotations.Email;
import com.zoowii.formutils.annotations.Length;
import com.zoowii.formutils.annotations.NotEmpty;
import com.zoowii.formutils.constraits.*;
import com.zoowii.formutils.exceptions.ValidateRuntimeException;


import javax.validation.constraints.*;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zoowii on 14/10/30.
 */
public class Validator {
    private final Map<Class<? extends Annotation>, Class<? extends Constrait<?>>> registeredValidationConstraints = new ConcurrentHashMap<>();

    public void registerConstraint(Class<? extends Annotation> validateAnnotationCls, Class<? extends Constrait<?>> constraintCls) {
        registeredValidationConstraints.put(validateAnnotationCls, constraintCls);
    }

    public Validator() {
        initialize();
    }

    public void initialize() {
        registerConstraint(NotNull.class, NotNullConstrait.class);
        registerConstraint(Null.class, NullConstrait.class);
        registerConstraint(AssertTrue.class, AssertTrueConstrait.class);
        registerConstraint(AssertFalse.class, AssertFalseConstrait.class);
        registerConstraint(DecimalMax.class, DecimalMaxConstrait.class);
        registerConstraint(DecimalMin.class, DecimalMinConstrait.class);
        registerConstraint(Digits.class, DigitsConstrait.class);
        registerConstraint(Email.class, EmailConstrait.class);
        registerConstraint(Future.class, FutureConstrait.class);
        registerConstraint(Length.class, LengthConstrait.class);
        registerConstraint(Max.class, MaxConstrait.class);
        registerConstraint(Min.class, MinConstrait.class);
        registerConstraint(NotEmpty.class, NotEmptyConstrait.class);
        registerConstraint(Past.class, PastConstrait.class);
        registerConstraint(Pattern.class, PatternConstrait.class);
        registerConstraint(Size.class, SizeConstrait.class);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends Constrait<?>> getConstraitClass(Class<? extends Annotation> validateAnnotation) {
        if (validateAnnotation == null) {
            return NotValidateConstrait.class;
        }
        return ValidateHelper.firstNotNull(registeredValidationConstraints.get(validateAnnotation), NotValidateConstrait.class);
    }

    public BindingResult validate(Object form) {
        if (form == null) {
            return null;
        }
        BindingResult bindingResult = new BindingResult();
        Class<?> cls = form.getClass();
        for (Field field : cls.getDeclaredFields()) {
            validateField(field, form, bindingResult);
        }
        return bindingResult;
    }

    /**
     * TODO: 如果注解的message不为null,就使用这个message作为error message
     *
     * @param field
     * @param form
     * @param bindingResult
     */
    public void validateField(Field field, Object form, BindingResult bindingResult) {
        if (field == null || bindingResult == null) {
            return;
        }
        FieldAccessor fieldAccessor = new FieldAccessor(form.getClass(), field.getName());
        Object fieldValue = fieldAccessor.getProperty(form);
        String fieldName = field.getName();
        ValidateErrorGenerator validateErrorGenerator = new ValidateErrorGenerator(field, fieldValue);
//        Constrait<? extends Annotation> constrait1;
//        NotNull notNullAnnotation = field.getAnnotation(NotNull.class);
        Class[] validateAnnotationClasses = new Class[]{
                NotNull.class, Null.class,
                AssertTrue.class, AssertFalse.class,
                Max.class, Min.class, DecimalMax.class, DecimalMin.class,
                Size.class, Digits.class,
                Past.class, Future.class,
                javax.validation.constraints.Pattern.class,
                NotEmpty.class, Length.class, Email.class};
        for (Class<?> validateaAnnotationCls : validateAnnotationClasses) {
            Annotation validateAnnotation = field.getAnnotation((Class<? extends Annotation>) validateaAnnotationCls);
            if (validateAnnotation != null) {
                Class<? extends Constrait<?>> constraitCls = getConstraitClass((Class<? extends Annotation>) validateaAnnotationCls);
                try {
                    Constrait<?> constrait = constraitCls.newInstance();
                    constrait.initialize(validateAnnotation);
                    if (constrait.isInValid(fieldValue)) {
                        bindingResult.addError(constrait.createValidateError(validateErrorGenerator));
                        return;
                    }
                } catch (InstantiationException e) {
                    throw new ValidateRuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new ValidateRuntimeException(e);
                }
            }
        }
//        if (notNullAnnotation != null) {
//            constrait1 = new NotNullConstrait();
//            constrait1.initialize(notNullAnnotation);
//            if (constrait1.isInValid(fieldValue)) {
//                bindingResult.addError(validateErrorGenerator.generate(notNullAnnotation, String.format("Field %s can't be null", fieldName)));
//                return;
//            }
//        }
//        Annotation nullAnnotation = field.getAnnotation(Null.class);
//        if (nullAnnotation != null && fieldValue != null) {
//            bindingResult.addError(validateErrorGenerator.generate(nullAnnotation, String.format("Field %s must be null", fieldName)));
//            return;
//        }
//        Annotation assertTrueAnnotation = field.getAnnotation(AssertTrue.class);
//        if (assertTrueAnnotation != null && fieldValue != null) {
//            if (!(fieldValue instanceof Boolean && BooleanUtils.isTrue((Boolean) fieldValue))) {
//                bindingResult.addError(validateErrorGenerator.generate(assertTrueAnnotation, String.format("Field %s must be true", fieldName)));
//                return;
//            }
//        }
//        Annotation assertFalseAnnotation = field.getAnnotation(AssertFalse.class);
//        if (assertFalseAnnotation != null && fieldValue != null) {
//            if (!(fieldValue instanceof Boolean && BooleanUtils.isFalse((Boolean) fieldValue))) {
//                bindingResult.addError(validateErrorGenerator.generate(assertFalseAnnotation, String.format("Field %s must be false", fieldName)));
//                return;
//            }
//        }
//        Min minAnnotation = field.getAnnotation(Min.class);
//        if (minAnnotation != null && fieldValue != null) {
//            boolean isError = false;
//            if (!(fieldValue instanceof Integer || fieldValue instanceof Long)) {
//                isError = true;
//            } else {
//                try {
//                    long fieldLongValue = Long.valueOf(fieldValue.toString());
//                    if (fieldLongValue < minAnnotation.value()) {
//                        isError = true;
//                    }
//                } catch (Exception e) {
//                    isError = true;
//                }
//            }
//            if (isError) {
//                bindingResult.addError(validateErrorGenerator.generate(minAnnotation, String.format("Field %s must be >= %d", fieldName, minAnnotation.value())));
//                return;
//            }
//        }
//        Max maxAnnotation = field.getAnnotation(Max.class);
//        if (maxAnnotation != null && fieldValue != null) {
//            boolean isError = false;
//            if (!(fieldValue instanceof Integer || fieldValue instanceof Long)) {
//                isError = true;
//            } else {
//                try {
//                    long fieldLongValue = Long.valueOf(fieldValue.toString());
//                    if (fieldLongValue > maxAnnotation.value()) {
//                        isError = true;
//                    }
//                } catch (Exception e) {
//                    isError = true;
//                }
//            }
//            if (isError) {
//                bindingResult.addError(validateErrorGenerator.generate(maxAnnotation, String.format("Field %s must be <= %d", fieldName, maxAnnotation.value())));
//                return;
//            }
//        }
//        DecimalMax decimalMax = field.getAnnotation(DecimalMax.class);
//        if (decimalMax != null && fieldValue != null) {
//            boolean isError = false;
//            if (!(fieldValue instanceof Integer || fieldValue instanceof Long || fieldValue instanceof Float || fieldValue instanceof Double)) {
//                isError = true;
//            } else {
//                try {
//                    Double fieldDoubleValue = Double.valueOf(fieldValue.toString());
//                    if (fieldDoubleValue > Double.valueOf(decimalMax.value())) {
//                        isError = true;
//                    }
//                } catch (Exception e) {
//                    isError = true;
//                }
//            }
//            if (isError) {
//                bindingResult.addError(validateErrorGenerator.generate(decimalMax, String.format("Field %s must be <= %f", fieldName, Double.valueOf(decimalMax.value()))));
//                return;
//            }
//        }
//        DecimalMin decimalMin = field.getAnnotation(DecimalMin.class);
//        if (decimalMin != null && fieldValue != null) {
//            boolean isError = false;
//            if (!(fieldValue instanceof Integer || fieldValue instanceof Long || fieldValue instanceof Float || fieldValue instanceof Double)) {
//                isError = true;
//            } else {
//                try {
//                    Double fieldDoubleValue = Double.valueOf(fieldValue.toString());
//                    if (fieldDoubleValue < Double.valueOf(decimalMin.value())) {
//                        isError = true;
//                    }
//                } catch (Exception e) {
//                    isError = true;
//                }
//            }
//            if (isError) {
//                bindingResult.addError(validateErrorGenerator.generate(decimalMin, String.format("Field %s must be >= %f", fieldName, Double.valueOf(decimalMin.value()))));
//                return;
//            }
//        }
//        Size sizeAnnotation = field.getAnnotation(Size.class);
//        if (sizeAnnotation != null && fieldValue != null) {
//            int size = fieldValue.toString().length();
//            if (size < sizeAnnotation.min() || size > sizeAnnotation.max()) {
//                bindingResult.addError(validateErrorGenerator.generate(sizeAnnotation, String.format("Field %s's length must be %d to %d", fieldName, sizeAnnotation.min(), sizeAnnotation.max())));
//                return;
//            }
//        }
//        Digits digitsAnnotation = field.getAnnotation(Digits.class);
//        if (digitsAnnotation != null && fieldValue != null) {
//            boolean isError = false;
//            try {
//                DigitsValidatorForNumber constraint = new DigitsValidatorForNumber();
//                constraint.initialize(digitsAnnotation);
//                if (!constraint.isValid(Double.valueOf(fieldValue.toString()), null)) {
//                    isError = true;
//                }
//            } catch (Exception e) {
//                isError = true;
//            }
//            if (isError) {
//                bindingResult.addError(validateErrorGenerator.generate(digitsAnnotation, String.format("Field %s must be as digits format (%d, %d)", fieldName, digitsAnnotation.integer(), digitsAnnotation.fraction())));
//                return;
//            }
//        }
//        Past pastAnnotation = field.getAnnotation(Past.class);
//        if (pastAnnotation != null && fieldValue != null) {
//            boolean isError = false;
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat();
//                Date fieldDateValue;
//                if (fieldValue instanceof Date) {
//                    fieldDateValue = (Date) fieldValue;
//                } else {
//                    fieldDateValue = sdf.parse(fieldValue.toString());
//                }
//                if (fieldDateValue.after(new Date())) {
//                    isError = true;
//                }
//            } catch (Exception e) {
//                isError = true;
//            }
//            if (isError) {
//                bindingResult.addError(validateErrorGenerator.generate(pastAnnotation, String.format("Field %s must be date before now", fieldName)));
//                return;
//            }
//        }
//        Future futureAnnotation = field.getAnnotation(Future.class);
//        if (futureAnnotation != null && fieldValue != null) {
//            boolean isError = false;
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat();
//                Date fieldDateValue;
//                if (fieldValue instanceof Date) {
//                    fieldDateValue = (Date) fieldValue;
//                } else {
//                    fieldDateValue = sdf.parse(fieldValue.toString());
//                }
//                if (fieldDateValue.before(new Date())) {
//                    isError = true;
//                }
//            } catch (Exception e) {
//                isError = true;
//            }
//            if (isError) {
//                bindingResult.addError(validateErrorGenerator.generate(futureAnnotation, String.format("Field %s must be date after now", fieldName)));
//                return;
//            }
//        }
//        javax.validation.constraints.Pattern patternAnnotation = field.getAnnotation(javax.validation.constraints.Pattern.class);
//        if (patternAnnotation != null && fieldValue != null) {
//            if (!fieldValue.toString().matches(patternAnnotation.regexp())) {
//                bindingResult.addError(validateErrorGenerator.generate(patternAnnotation, String.format("Field %s must match regex %s", fieldName, patternAnnotation.regexp())));
//                return;
//            }
//        }
//        NotEmpty notEmptyAnnotation = field.getAnnotation(NotEmpty.class);
//        if (notEmptyAnnotation != null && (fieldValue == null || fieldValue.toString().trim().isEmpty())) {
//            bindingResult.addError(validateErrorGenerator.generate(notEmptyAnnotation, String.format("Field %s can't be empty", fieldName)));
//            return;
//        }
//        Length lengthAnnotation = field.getAnnotation(Length.class);
//        if (lengthAnnotation != null && fieldValue != null) {
//            int fieldValueLength = fieldValue.toString().length();
//            if (fieldValueLength < lengthAnnotation.min() || fieldValueLength > lengthAnnotation.max()) {
//                bindingResult.addError(validateErrorGenerator.generate(lengthAnnotation, String.format("Field %s's length must be %d to %d", fieldName, lengthAnnotation.min(), lengthAnnotation.max())));
//                return;
//            }
//        }
//        Email emailAnnotation = field.getAnnotation(Email.class);
//        if (emailAnnotation != null && fieldValue != null) {
//            if (!fieldValue.toString().matches("^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$")) {
//                bindingResult.addError(validateErrorGenerator.generate(emailAnnotation, String.format("Field %s must be email format", fieldName)));
//                return;
//            }
//        }
    }
}
