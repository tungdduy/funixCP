package com.funix.capstone.converters;

public interface FormConverter<DTO> {
    DTO convertToDTO(String formJson);
}
