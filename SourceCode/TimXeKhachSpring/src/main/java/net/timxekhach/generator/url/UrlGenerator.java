package net.timxekhach.generator.url;

import net.timxekhach.security.SecurityDeclare;

import static net.timxekhach.generator.GeneratorCenter.API_ROOT;

public class UrlGenerator {
    public static final String
            TEMPLATE_URL_PATH = API_ROOT + "generator/url/ftl-template/",
            APP_URL_TEMPLATE_DIR = TEMPLATE_URL_PATH + "app/",
            TEMPLATE_URL_API_DIR = TEMPLATE_URL_PATH + "api/";

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        SecurityDeclare.startBuildUrl();
        AppUrlGenerator.generateAppUrls();
        RouterGenerator.generateRouters();
        UrlImportGenerator.generateUrlImports();
    }


}
