package com.zhmenko.util;

import com.zhmenko.exception.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtils {
    private static Validator validator;

    private ValidationUtils() {
    }

    public static void validate(Object object) {
        if (validator == null) init();
        final Set<ConstraintViolation<Object>> errors = validator.validate(object);
        if (errors.isEmpty()) return;
        final String errorsMessages = errors
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        throw new BadRequestException(errorsMessages);
    }

    private static void init() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }
}
