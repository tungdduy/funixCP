package net.timxekhach.utility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class XeStringUtils extends StringUtils {
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String EMPTY_STRING = "";
    public static final String PHONE_REGEX = "(03|05|07|08|09)+\\d{8,10}";

    public static List<String> splitByComma(String value) {
        XeStringUtils.splitByCharacterType("asd");
        return Optional.of(
                Arrays.asList(XeStringUtils.split(value, XeStringUtils.COMMA)))
                .orElse(new ArrayList<>());
    }

    public static String joinByColon(List<String> values) {
        return join(values, COLON);
    }

    public static String expressToString(Object o) {
        if(o == null) {
            return EMPTY_STRING;
        }
        Object[] arrChecker = o.getClass().isArray() ? (Object[]) o : new Object[]{o};

        List<String> toList = Arrays.stream(arrChecker).map(s -> {
            if(s instanceof Class) {
                return ((Class<?>) s).getName();
            } else if (ClassUtils.isPrimitiveOrWrapper(s.getClass())){
                return s.toString();
            } else {
                return EMPTY_STRING;
            }
        }).filter(XeStringUtils::isNotBlank)
                .collect(Collectors.toList());
       return joinByComma(toList);
    }

    public static String joinByComma(List<String> values) {
        return join(values, COMMA);
    }
}