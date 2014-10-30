package com.zoowii.formutils.annotations;

/**
 * Created by zoowii on 14/10/30.
 */
public @interface Range {
    public long min() default 0;

    public long max() default Long.MAX_VALUE;

    public String message() default "";
}
