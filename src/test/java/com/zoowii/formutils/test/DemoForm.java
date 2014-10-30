package com.zoowii.formutils.test;

import com.zoowii.formutils.annotations.Email;
import com.zoowii.formutils.annotations.Length;
import com.zoowii.formutils.annotations.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * Created by zoowii on 14/10/30.
 */
public class DemoForm {
    @NotNull
    @Length(min = 4, max = 30)
    private String name;
    @Email
    @NotEmpty(message = "The email can't be empty")
    private String email;
    @Min(18)
    @Max(150)
    private int age;
    @Past
    private Date createTime;

    @Valid
    private DemoForm subForm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public DemoForm getSubForm() {
        return subForm;
    }

    public void setSubForm(DemoForm subForm) {
        this.subForm = subForm;
    }
}
