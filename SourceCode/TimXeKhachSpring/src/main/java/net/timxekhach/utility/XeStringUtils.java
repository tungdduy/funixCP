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
        XeStringUtils.splitByCharacterType(COMMA);
        return Optional.of(
                Arrays.asList(XeStringUtils.split(value, XeStringUtils.COMMA)))
                .orElse(new ArrayList<>());
    }

    public static String joinByColon(List<String> values) {
        return join(values, COLON);
    }

    public static String expressListToString(Object o) {
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

    public static String removeLastChar(String string, int i) {
        return string.substring(0, string.length() - i);
    }

    /**
     * @param cssCase like: css-case-with-hyphen
     * @return CapitalizeEachWord
     */
    public static String cssCaseToCapitalizeEachWord(String cssCase){
        return Arrays.stream(cssCase.split("-"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }
}