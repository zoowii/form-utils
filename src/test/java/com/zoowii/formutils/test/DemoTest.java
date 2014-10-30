package com.zoowii.formutils.test;

import com.zoowii.formutils.BindingResult;
import com.zoowii.formutils.Validator;
import com.zoowii.formutils.ValidatorFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zoowii on 14/10/30.
 */
public class DemoTest {
    @Test
    public void testDemoForm() {
        Validator validator = ValidatorFactory.getValidator();
        DemoForm demoForm = new DemoForm();
        demoForm.setName(null);
        demoForm.setEmail("test@email.com");
        demoForm.setAge(200);
        BindingResult bindingResult = validator.validate(demoForm);
        Assert.assertEquals(bindingResult.getErrorCount(), 2);
    }
}
