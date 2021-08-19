package net.timxekhach.utility;

public class XeNumberUtils {
    public static boolean isOverlap(Integer range1From, Integer range1To, Integer range2From, Integer range2To) {
        return XeObjectUtils.anyNull(range1From, range1To, range2From, range2To)
                || (range1From <= range2From && range1To >= range2From)
                || (range1From <= range2To && range1To >= range2To)
                || (range2From <= range1From && range2To >= range1From)
                || (range2From <= range1To && range2To >= range1To)
                ;
    }

    public static int zeroIfNull(Integer value) {
        return value == null ? 0 : value;
    }
}
