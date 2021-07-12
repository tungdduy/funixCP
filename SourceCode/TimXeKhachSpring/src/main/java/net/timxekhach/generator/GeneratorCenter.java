package net.timxekhach.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import net.timxekhach.generator.abstracts.AbstractTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.generator.abstracts.url.AbstractUrlTemplateBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Map;

public class GeneratorCenter {

    public static final String
            API_ROOT = "SourceCode/TimXeKhachSpring/src/main/java/net/timxekhach/",
            API_OPERATION_REST_ROOT = API_ROOT + "operation/rest/",
            API_OPERATION_REST_API_ROOT = API_OPERATION_REST_ROOT + "api/",
            API_OPERATION_REST_SERVICE_ROOT = API_OPERATION_REST_ROOT + "service/",
            GENERATOR_ROOT = API_ROOT + "generator/",
            TEMPLATE_ROOT = GENERATOR_ROOT + "templates/";

    public static void main(String[] args) {
        AbstractUrlTemplateBuilder.buildUrlFiles();
        AbstractTemplateBuilder.buildAll();
    }

    private static Configuration config;

    public static Configuration getConfig() {
        if (config == null) {
            config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            config.setLogTemplateExceptions(false);
            config.setWrapUncheckedExceptions(true);
        }
        return config;
    }


    public static <E extends AbstractTemplateSource> void writeToNoneExistFileOnly(Map<String, E> input, Template template, File file) {
        if (file.exists()) {
            return;
        }
        writeToFile(input, template, file);
    }

    public static <E extends AbstractTemplateSource> void writeToFile(Map<String, E> input, Template template, File realFile)  {
        if(realFile == null) return;
        realFile.getParentFile().mkdirs();
        try (Writer fileWriter = new FileWriter(realFile)){
            template.process(input, fileWriter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static Template prepareTemplate(File templateFile) {
        try {
            getConfig().setDirectoryForTemplateLoading(templateFile.getParentFile());
            return config.getTemplate(templateFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

