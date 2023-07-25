package com.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import org.passay.*;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

  @Override
  public void initialize(ValidPassword arg0) {}

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    PasswordValidator validator =
        new PasswordValidator(
            Arrays.asList(
                new LengthRule(8, 30),
                new UppercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new SpecialCharacterRule(1),
                new WhitespaceRule()));

    RuleResult result = validator.validate(new PasswordData(password));
    if (result.isValid()) {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context
        .buildConstraintViolationWithTemplate(validator.getMessages(result).get(0))
        .addConstraintViolation();
    return false;
  }
}
