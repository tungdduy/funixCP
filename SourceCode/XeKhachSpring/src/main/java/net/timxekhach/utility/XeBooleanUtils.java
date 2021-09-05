package net.timxekhach.utility;

public class XeBooleanUtils {
    public static boolean isTrue(String content) {
        return XeStringUtils.isNotBlank(content) && content.trim().equalsIgnoreCase("true");
    }
}
