package com.zoowii.formutils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zoowii on 14/10/30.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
    public int min() default 1;

    public int max() default Integer.MAX_VALUE;

    public String message() default "";
}
