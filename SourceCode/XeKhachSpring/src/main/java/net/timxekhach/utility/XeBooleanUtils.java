package net.timxekhach.utility;

import org.apache.commons.lang3.BooleanUtils;

public class XeBooleanUtils {
    public static boolean isTrue(String content) {
        return XeStringUtils.isNotBlank(content) && content.trim().equalsIgnoreCase("true");
    }
}
