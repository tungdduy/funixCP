package generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import generator.abstracts.models.AbstractModel;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Map;

public class GeneratorSetup {
    public static final String
            API_ROOT = "SourceCode/XeKhachSpring/src/main/java/net/timxekhach/",
            API_OPERATION_ROOT = API_ROOT + "operation/",
            API_OPERATION_DATA_ROOT = API_OPERATION_ROOT + "data/",
            API_OPERATION_DATA_ENTITY_ROOT = API_OPERATION_DATA_ROOT + "entity/",
            API_OPERATION_DATA_MAPPED_ROOT = API_OPERATION_DATA_ROOT + "mapped/",
            API_OPERATION_DATA_REPOSITORY_ROOT = API_OPERATION_DATA_ROOT + "repository/",
            API_OPERATION_REST_ROOT = API_OPERATION_ROOT + "rest/",
            API_OPERATION_REST_API_ROOT = API_OPERATION_REST_ROOT + "api/",
            API_OPERATION_REST_SERVICE_ROOT = API_OPERATION_REST_ROOT + "service/",
            TOOLS_ROOT = "SourceCode/XeKhachTools/src/main/java/",
            TOOL_GENERATOR_ROOT = TOOLS_ROOT + "generator/";


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


    public static <E extends AbstractModel> void writeToNoneExistFileOnly(Map<String, E> input, Template template, File file) {
        if (file.exists()) {
            return;
        }
        writeToFile(input, template, file);
    }

    public static <E extends AbstractModel> void writeToFile(Map<String, E> input, Template template, File realFile)  {
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
