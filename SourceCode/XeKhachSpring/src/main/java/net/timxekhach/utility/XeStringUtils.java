package net.timxekhach.utility;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XeStringUtils {
    public static final String COMMA = ",",
            PHONE_REGEX = "(03|05|07|08|09)+\\d{8,10}";

    public static String getNoneDigitChars(String value) {
        return isBlank(value) ? "" : value.replaceAll("[^0-9]+", "");
    }

    public static void main(String[] args) {
        System.out.println(getNoneDigitChars("894fJF,.2131,312 4"));
    }

    public static List<String> splitByComma(String value) {
        StringUtils.splitByCharacterType(COMMA);
        return Optional.of(
                Arrays.asList(StringUtils.split(value, XeStringUtils.COMMA)))
                .orElse(new ArrayList<>());
    }

    public static boolean isBlank(String value) {
        return StringUtils.isBlank(value);
    }

    public static boolean isNotBlank(String value) {
        return !StringUtils.isBlank(value);
    }

    public static String trimToEmpty(String trim) {
        return trim == null ? "" : trim.trim();
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

    public static String getStringBetween(String source, String open, String end) {
        return StringUtils.substringBetween(source, open, end);
    }

    public static Stream<Integer> commaGt0ToStream(String commaList) {
        return Arrays.stream(XeStringUtils.trimToEmpty(commaList).split(",")).map(seat -> {
            try {
                return Integer.parseInt(seat);
            } catch (Exception ex) {
                return 0;
            }
        }).filter(n -> n > 0);
    }

}
