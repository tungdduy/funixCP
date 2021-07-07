package net.timxekhach.generator.url;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.timxekhach.generator.GeneratorCenter;
import net.timxekhach.generator.url.model.ComponentModel;
import net.timxekhach.generator.url.model.RouterModel;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.security.handler.UrlBuilder;
import net.timxekhach.utility.XeFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.timxekhach.generator.url.UrlGenerator.APP_URL_TEMPLATE_DIR;

public class RouterGenerator {
    public static final String
            ROUTER_MODULE_TEMPLATE_NAME = "routing.module.ts.ftl",
            ROUTER_COMPONENT_TEMPLATE_NAME = "router.component.ts.ftl",
            MODULE_TEMPLATE_NAME = "module.ts.ftl",
            COMPONENT_TEMPLATE = "component.ts.ftl"
    ;

    public static void generateRouters() {
        Configuration config = GeneratorCenter.getConfig();
        Template routingModuleTemplate = GeneratorCenter.prepareTemplate(config, APP_URL_TEMPLATE_DIR, ROUTER_MODULE_TEMPLATE_NAME);
        Template routerComponentTemplate = GeneratorCenter.prepareTemplate(config, APP_URL_TEMPLATE_DIR, ROUTER_COMPONENT_TEMPLATE_NAME);
        Template moduleTemplate = GeneratorCenter.prepareTemplate(config, APP_URL_TEMPLATE_DIR, MODULE_TEMPLATE_NAME);
        Template componentTemplate = GeneratorCenter.prepareTemplate(config, APP_URL_TEMPLATE_DIR, COMPONENT_TEMPLATE);
        fetchRouterModels(new ArrayList<>(), UrlBuilder.appUrls)
                .forEach(module -> {
            Map<String, Object> input = new HashMap<>();
            input.put("router", module);
            generateFile(config, module.getRoutingModulePath(), routingModuleTemplate, input);
            generateFile(config, module.getRouterComponentPath(), routerComponentTemplate, input);
            generateFile(config, module.getModulePath(), moduleTemplate, input);
            XeFileUtil.createFile(module.getHtmlPath());
            XeFileUtil.createFile(module.getScssPath());
            generateChildrenFiles(config, module.getChildren(), componentTemplate);
        });
    }

    private static void generateChildrenFiles(Configuration config, List<ComponentModel> children, Template template) {
        Map<String, Object> input = new HashMap<>();
        children.forEach(child -> {
            input.put("component", child);
            generateFile(config, child.getComponentPath(), template, input);
            XeFileUtil.createFile(child.getHtmlPath());
            XeFileUtil.createFile(child.getScssPath());
        });
    }

    private static void generateFile(Configuration config, String filePath, Template template, Map<String, Object> input) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            GeneratorCenter.writeToFile(input, template, new File(filePath));
        }
    }

    private static List<RouterModel> fetchRouterModels(ArrayList<RouterModel> routerModels, List<UrlNode> appUrls) {
        appUrls.forEach(appUrl -> {
            if(!appUrl.getChildren().isEmpty()) {
                routerModels.add(new RouterModel(appUrl));
                fetchRouterModels(routerModels, appUrl.getChildren());
            }
        });
        return routerModels;
    }

}
