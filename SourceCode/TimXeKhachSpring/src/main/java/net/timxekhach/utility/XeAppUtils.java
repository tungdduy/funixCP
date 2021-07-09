package net.timxekhach.utility;

public class XeAppUtils {

    public static final String
            APP_ROOT = "SourceCode/XeKhachAngular/src/app/",
            BUSINESS_DIR = APP_ROOT + "business/",
            PAGES_DIR = BUSINESS_DIR + "pages/",
            I18N_DIR = BUSINESS_DIR + "i18n/",
            MESSAGE_PATH = I18N_DIR + "/api-messages_vi.ts",
            FRAMEWORK_DIR = APP_ROOT + "framework/",
            FRAMEWORK_URL_DIR = FRAMEWORK_DIR + "url/"
    ;

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
