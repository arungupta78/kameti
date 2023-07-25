package com.demo.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface ValidPassword {

  String message() default "Invalid Password";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
