package com.hrmtool.utils;

import com.hrmtool.globalHandler.BadRequestException;
import com.hrmtool.persistance.dto.OrganizationDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static java.util.Objects.isNull;

public class RequestValidator {
    public static <T> void validateRequest(T request) throws BadRequestException {
        if (isNull(request)) {
            throw new BadRequestException("Request can not be null");
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String violationMessage = null;
            for (ConstraintViolation<T> violation : violations) {
                violationMessage = violation.getMessageTemplate();
                break;
            }
            throw new BadRequestException(violationMessage);
        }
    }
}
