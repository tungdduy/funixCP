package net.timxekhach.utility;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class XeStringUtils extends StringUtils {
    public static final String COMMA = ",",
            PHONE_REGEX = "(03|05|07|08|09)+\\d{8,10}";

    public static List<String> splitByComma(String value) {
        XeStringUtils.splitByCharacterType(COMMA);
        return Optional.of(
                Arrays.asList(XeStringUtils.split(value, XeStringUtils.COMMA)))
                .orElse(new ArrayList<>());
    }

    public static String randomAlphaNumerics(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


}