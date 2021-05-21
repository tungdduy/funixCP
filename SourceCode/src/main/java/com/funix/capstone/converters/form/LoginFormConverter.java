package com.funix.capstone.converters.form;

import com.funix.capstone.converters.FormConverter;
import com.funix.capstone.model.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

public class LoginFormConverter implements FormConverter<UserDTO> {
    @Override
    public UserDTO convertToDTO(HttpServletRequest request) {

        UserDTO userDTO = new UserDTO();

        return userDTO;
    }
}
