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


import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
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
        validateForm(form, bindingResult);
        return bindingResult;
    }

    private void validateForm(Object form, BindingResult bindingResult) {
        if (form == null || bindingResult == null) {
            return;
        }
        Class<?> cls = form.getClass();
        for (Field field : cls.getDeclaredFields()) {
            Class<?> fieldType = field.getType();
            if (fieldType.isPrimitive() || fieldType == String.class || Number.class.isAssignableFrom(fieldType) || fieldType == Boolean.class || Date.class.isAssignableFrom(fieldType)) {
                validateField(field, form, bindingResult);
            } else {
                if (field.getAnnotation(Valid.class) != null) {
                    FieldAccessor fieldAccessor = new FieldAccessor(form.getClass(), field.getName());
                    validateForm(fieldAccessor.getProperty(form), bindingResult);
                }
            }
        }
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
    }
}
