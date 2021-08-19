package net.timxekhach.utility;

import java.util.Arrays;
import java.util.Objects;

public class XeObjectUtils {
    public static boolean anyNull(Object... obj) {
        return obj == null || Arrays.stream(obj).anyMatch(Objects::isNull);
    }

}
