package com.funix.capstone.validators;

import com.funix.capstone.exception.BadInputException;
import com.funix.capstone.model.form.AbstractUIForm;

public interface FormValidator {
    <T extends AbstractUIForm> boolean validate(T form) throws BadInputException;
}
