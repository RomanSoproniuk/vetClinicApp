package com.coolvetclinicpumb.vetclinicapp.validation;

import com.coolvetclinicpumb.vetclinicapp.dto.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {
    @Override
    public boolean isValid(UserRegistrationRequestDto value,
                           ConstraintValidatorContext context) {
        return value.getPassword() != null
                && Objects.equals(value.getPassword(), value.getRepeatPassword());
    }
}
