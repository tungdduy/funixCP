package net.timxekhach.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import net.timxekhach.generator.auths.AuthsGenerator;
import net.timxekhach.generator.message.MessageGenerator;
import net.timxekhach.generator.url.UrlGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class GeneratorCenter {

    public static final String
            APP_ROOT = "SourceCode/XeKhachAngular/src/app/",
            API_ROOT = "SourceCode/TimXeKhachSpring/src/main/java/net/timxekhach/",
            APP_PAGES_DIR = APP_ROOT + "business/pages/";

    public static void main(String[] args){
        UrlGenerator.run();
        AuthsGenerator.run();
        MessageGenerator.run();
    }

    private static Configuration config;
    public static Configuration getConfig() {
        if(config == null) {
            config = prepareConfig();
        }
        return config;
    }


    @NotNull
    private static Configuration prepareConfig() {
        Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(false);
        config.setWrapUncheckedExceptions(true);
        return config;
    }

    public static void writeToFile(Map<String, Object> input, Template template, File realFile) {
        try {
            write(input, template,realFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void write(Map<String, Object> input, Template template, File realFile) throws IOException, TemplateException {
        Writer fileWriter = new FileWriter(realFile);
        try {
             template.process(input, fileWriter);
        } finally {
             fileWriter.close();
        }
    }

    public static Template prepareTemplate(Configuration config, String templatePath, String templateName){
        try {
            config.setDirectoryForTemplateLoading(new File(templatePath));
            Template template = config.getTemplate(templateName);
            return template;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
