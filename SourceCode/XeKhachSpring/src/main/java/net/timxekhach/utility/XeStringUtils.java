package net.timxekhach.utility;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class XeStringUtils extends StringUtils {
    public static final String COMMA = ",",
            PHONE_REGEX = "(03|05|07|08|09)+\\d{8,10}";


    public static List<String> splitByComma(String value) {
        XeStringUtils.splitByCharacterType(COMMA);
        return Optional.of(
                Arrays.asList(XeStringUtils.split(value, XeStringUtils.COMMA)))
                .orElse(new ArrayList<>());
    }


    public static String idTrim(String value) {
        return value == null ? null : value.trim().toUpperCase().replaceAll("[^A-Za-z0-9]+", "");
    }
}