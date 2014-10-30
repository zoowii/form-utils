package com.zoowii.formutils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zoowii on 14/10/30.
 */
public class ValidateHelper {
    public static <T> T firstNotNull(T... items) {
        for (T item : items) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    public static String firstNotEmptyString(String... items) {
        for (String item : items) {
            if (!StringUtils.isEmpty(item)) {
                return item;
            }
        }
        return null;
    }
}
