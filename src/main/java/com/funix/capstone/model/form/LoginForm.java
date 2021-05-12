package com.funix.capstone.model.form;

import lombok.Data;

@Data
public class LoginForm extends AbstractUIForm{
    private String username;
    private String password;
}
