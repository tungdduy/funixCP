package util;

public class AppUtil {

    public static final String
            APP_ROOT = "SourceCode/XeKhachAngular/src/app/",
            BUSINESS_DIR = APP_ROOT + "business/",
            PAGES_DIR = BUSINESS_DIR + "pages/",
            I18N_DIR = BUSINESS_DIR + "i18n/",
            MESSAGE_PATH = I18N_DIR + "/api-messages_vi.ts",
            FRAMEWORK_DIR = APP_ROOT + "framework/",
            FRAMEWORK_URL_DIR = FRAMEWORK_DIR + "url/",
            FRAMEWORK_UTIL_DIR = FRAMEWORK_DIR + "util/"

    ;

    public static String getPathToFramework(int level) {
        return StringUtil.repeat("../", level + 3);
    }

    public static String getPathToI18n(int level) {
        return StringUtil.repeat("../", level + 2);
    }

    public static String getPathToAbstract(int level) {
        return StringUtil.repeat("../", level + 2);
    }

}
