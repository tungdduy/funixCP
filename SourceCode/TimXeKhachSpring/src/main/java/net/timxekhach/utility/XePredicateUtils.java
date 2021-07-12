package net.timxekhach.utility;

import java.util.function.Predicate;

public class XePredicateUtils {
    public static <T> Predicate<T> negate(Predicate<T> predicate) {
        return predicate.negate();
    }
}
