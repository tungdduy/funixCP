package net.timxekhach.utility;

public class XeNumberUtils {
    public static boolean isInRange(Integer testing, Integer first, Integer last) {
        int min = Math.min(first, last);
        int max = Math.max(first, last);
        return testing >= min && testing <= max;
    }

    public static boolean anyNullOrOverlap(Integer range1A, Integer range1B, Integer range2A, Integer range2B) {

        return XeObjectUtils.anyNull(range1A, range1B, range2A, range2B)
                || isInRange(range1A, range2A, range2B)
                || isInRange(range1B, range2A, range2B)
                || isInRange(range2A, range1A, range1B)
                || isInRange(range2B, range1A, range1B);
    }

    public static boolean notEqual(Number a, Number b) {
        return !equal(a, b);
    }
    public static boolean equal(Number a, Number b) {
        return a != null && a.equals(b);
    }

    public static int zeroIfNull(Integer value) {
        return value == null ? 0 : value;
    }
}
