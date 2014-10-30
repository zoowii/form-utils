FormUtils
====
Yet another form validator library


## Usage

    First, deploy this project to your local maven repository
    then add the dependency:
    <dependency>
      <groupId>com.zoowii</groupId>
      <artifactId>formutils</artifactId>
      <version>1.0.0</version>
    </dependency>

    // define the form class
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

        ... getters and setters
    }

    // validate form
    Validator validator = ValidatorFactory.getValidator();
    DemoForm demoForm = new DemoForm();
    demoForm.setName(null);
    demoForm.setEmail("test@email.com");
    demoForm.setAge(200);
    BindingResult bindingResult = validator.validate(demoForm);
    Assert.assertEquals(bindingResult.getErrorCount(), 2);

    // you can also define your own validation annotation and the mapped constrait class
    // just extend the Constrait<T extends Annotation> class