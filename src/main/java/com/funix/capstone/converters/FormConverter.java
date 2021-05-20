package com.funix.capstone.converters;

import javax.servlet.http.HttpServletRequest;

public interface FormConverter<DTO> {
    DTO convertToDTO(HttpServletRequest request);

}
