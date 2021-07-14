package util;

import java.util.function.Predicate;

public class PredicateUtil {
    public static <T> Predicate<T> negate(Predicate<T> predicate) {
        return predicate.negate();
    }
}
