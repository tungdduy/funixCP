package net.timxekhach.utility;

public class XeAppUtils {
    public static String getPathToFramework(int level) {
        return XeStringUtils.repeat("../", level + 3);
    }

    public static String getPathToI18n(int level) {
        return XeStringUtils.repeat("../", level + 2);
    }

    public static String getPathToAbstract(int level) {
        return XeStringUtils.repeat("../", level + 2);
    }

    public static String getHtmlPath(String prefix) {
        return prefix + ".component.html";
    }

    public static String getScssPath(String prefix) {
        return prefix + ".component.scss";
    }

    public static String getComponentPath(String prefix) {
        return prefix + ".component.ts";
    }
}
