package net.timxekhach.generator.url;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.timxekhach.generator.GeneratorCenter;
import net.timxekhach.generator.url.model.UrlImport;
import net.timxekhach.security.handler.UrlBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.timxekhach.generator.GeneratorCenter.APP_ROOT;
import static net.timxekhach.generator.url.UrlGenerator.APP_URL_TEMPLATE_DIR;

public class UrlImportGenerator {
    public static final String
            URL_IMPORT_TEMPLATE_NAME = "url.import.ts.ftl",
            URL_IMPORT_APP_PATH = APP_ROOT + "framework/url/url.import.ts";

    public static void generateUrlImports(){
        Configuration config = GeneratorCenter.getConfig();
        Template urlImportsTemplate = GeneratorCenter.prepareTemplate(config, APP_URL_TEMPLATE_DIR, URL_IMPORT_TEMPLATE_NAME);
        Map<String, Object> input = new HashMap<>();
        input.put("urlImports", UrlImport.build(new ArrayList<>(), UrlBuilder.appUrls));
        GeneratorCenter.writeToFile(input, urlImportsTemplate, new File(URL_IMPORT_APP_PATH));
    }
}
