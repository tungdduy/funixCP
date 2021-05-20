package com.funix.capstone.validators;

import com.funix.capstone.exception.BadInputException;
import com.funix.capstone.model.form.AbstractUIForm;
import com.funix.capstone.model.form.LoginForm;

public class CustomerLoginFormValidator implements FormValidator {
    @Override
    public <T extends AbstractUIForm> boolean validate(T form) throws BadInputException {
        LoginForm loginForm = (LoginForm) form;

        if (loginForm == null)
            throw new BadInputException("Bad Input");

        if ( loginForm.getUsername() == null || loginForm.getUsername().isEmpty())
            throw new BadInputException("Please enter username");

        if ( loginForm.getPassword() == null || loginForm.getPassword().isEmpty())
            throw new BadInputException("Please enter password");

        return true;
    }
}
