package util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class StringUtil extends StringUtils {
    static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static final String COMMA = ",",
            DOT = ".",
            EMPTY_STRING = "",
            EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,6}$",
            PHONE_REGEX = "(03|05|07|08|09)+\\\\d{8,10}",
            NONE_ALPHA_REGEX = "[^a-zA-Z]+";

    public static String fetchSeparatorContent(String separator, String content) {
        try {
            return content.split(separator)[1].trim();
        } catch (Exception ignored) {
//            logger.error("content is null or has no separator!");
        }
        return "";
    }
    public static String[] fetchSplitter(String separator, String content) {
        try {
            String[] result = content.split(separator);
            if(result.length < 3) return null;
            return result;
        } catch (Exception ignored) {
        }
        return null;
    }

    public static List<String> splitByComma(String value) {
        splitByCharacterType(COMMA);
        return Optional.of(
                Arrays.asList(split(value, COMMA)))
                .orElse(new ArrayList<>());
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
        }).filter(StringUtil::isNotBlank)
                .collect(Collectors.toList());
        return joinByComma(toList);
    }

    public static String joinByComma(List<String> values) {
        return join(values, COMMA);
    }


    public static String removeLastChar(String string, int i) {
        return string.substring(0, string.length() - i);
    }

    public static String joinByDot(String... values) {
        return join(values, DOT);
    }

    public static String joinByDot(List<String> values) {
        return join(values, DOT);
    }

    private static String join(String[] strings, String joiner) {
        if(strings == null) return "";
        List<String> result = new ArrayList<>();
        for (String string: strings) {
            if (isNotBlank(string)) {
                result.add(string);
            }
        }
        return String.join(joiner, result);
    }

    public static String joinAsArguments(String... strings) {
        return join(strings, ", ");
    }

    public static String join(List<String> strings, String joiner) {
        return join(strings.toArray(new String[0]), joiner);
    }

    public static String toLowercaseCharsOnly(String value) {
        return value == null ? "" : value.trim()
                .replaceAll(NONE_ALPHA_REGEX, "")
                .toLowerCase();
    }

    public static String toUPPER_UNDERLINE(String value) {
        return value == null ? ""
                : Arrays.stream(value.trim().split(NONE_ALPHA_REGEX))
                .flatMap(s -> Arrays.stream(splitByCapital(s)))
                .map(String::toUpperCase)
                .collect(Collectors.joining("_"));
    }

    public static String toCapitalizeEachWord(String value) {
        return value == null ? "" : Arrays.stream(value.trim()
                .split(NONE_ALPHA_REGEX))
                .map(s -> s.substring(0,1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }

    public static String toCamel(String value) {
        String capitalize = toCapitalizeEachWord(value);
        if(capitalize.length() > 0) {
            return capitalize.substring(0, 1).toLowerCase() + capitalize.substring(1);
        }
        return "";
    }

    public static String toIdName(Object obj) {
        return toCamel(obj.getClass().getSimpleName()) + "Id";
    }

    public static String trimToEmpty(String value) {
        return value == null ? "": value.trim();
    }


    public static String[] splitByCapital(String value) {
        return value.split("(?=\\p{Upper})");
    }

    public static String capitalizeEachWordToLowerDotChain(String CapitalizeNameLikeThis) {
        String[] words = splitByCapital(CapitalizeNameLikeThis);
        return Arrays.stream(words).map(String::toLowerCase).collect(Collectors.joining("."));
    }

    public static String buildSeparator(String name) {
        return String.format("// ____________________ ::%s_SEPARATOR:: ____________________ //", name);
    }

    public static List<String> toImportFormat(List<String> importClasses) {
        return importClasses.stream()
                .map(s -> format("import %s;", s))
                .collect(toList());
    }

    public static List<String> toImportFormat(String... importClasses) {
        return toImportFormat(Arrays.asList(importClasses));
    }

    public static String wrapInQuote(String value) {
        return String.format("\"%s\"", value);
    }


}
