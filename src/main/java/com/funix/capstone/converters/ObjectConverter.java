package com.funix.capstone.converters;

public interface ObjectConverter<Form> {
    Form convertToForm(String objectJson);
}
