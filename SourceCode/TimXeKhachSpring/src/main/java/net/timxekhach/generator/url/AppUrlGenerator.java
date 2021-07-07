package net.timxekhach.generator.url;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.timxekhach.generator.GeneratorCenter;
import net.timxekhach.generator.url.model.UrlTemplate;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.security.handler.UrlBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.timxekhach.generator.GeneratorCenter.APP_ROOT;

public class AppUrlGenerator {
    public static final String
            APP_URL_TEMPLATE_NAME = "url.declare.ts.ftl",
            APP_URL_FILE_PATH = APP_ROOT + "framework/url/url.declare.ts";

    public static void generateAppUrls() {
        Map<String, Object> input = prepareUrlTemplateInputs();
        Configuration config = GeneratorCenter.getConfig();
        Template template = GeneratorCenter.prepareTemplate(config, UrlGenerator.APP_URL_TEMPLATE_DIR, APP_URL_TEMPLATE_NAME);
        GeneratorCenter.writeToFile(input, template, new File(APP_URL_FILE_PATH));
    }

    @NotNull
    private static Map<String, Object> prepareUrlTemplateInputs() {
        List<UrlTemplate> apiUrls = new ArrayList<>();
        UrlBuilder.apiUrls.forEach(api -> {
            generateUrlTemplates(apiUrls, api);
        });
        List<UrlTemplate> appUrls = new ArrayList<>();
        UrlBuilder.appUrls.forEach(app -> {
            generateUrlTemplates(appUrls, app);
        });
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("appUrls", appUrls);
        input.put("apiUrls", apiUrls);
        return input;
    }


    static void generateUrlTemplates(List<UrlTemplate> templates, UrlNode url) {
        UrlTemplate template = getTemplate(url);
        templates.add(template);
        processChildTemplates(url, template);
    }

    private static String getConfig(UrlNode url) {
        return  "config()"
                + publicConfig(url)
                + authAndRolesConfig(url);

    }

    private static UrlTemplate getTemplate(UrlNode url) {
        return new UrlTemplate(url.buildUpperCaseUrl(), getConfig(url));
    }

    static void processChildTemplates(UrlNode parentBuilder, UrlTemplate parentTemplate) {
        parentBuilder.getChildren().forEach(childBuilder -> {
            UrlTemplate childTemplate = getTemplate(childBuilder);
            parentTemplate.getChildren().add(childTemplate);
            processChildTemplates(childBuilder, childTemplate);
        });
    }

    private static String publicConfig(UrlNode url) {
        return url.getIsPublic() != null? ".public()" : "";
    }

    /**
     * @return string like .auths([r.ROLE_ADMIN, a.USER_READ])
     * must be consistent with the app config
     */
    private static String authAndRolesConfig(UrlNode url) {
        List<String> auths = url.getAuths().stream()
                .map(a -> "a." + a.name())
                .collect(Collectors.toList());
        List<String> roles = url.getRoles().stream()
                .map(r -> "r." + r.name())
                .collect(Collectors.toList());
        roles.addAll(auths);
        String authsAndRoles = String.join(", ", roles);
        if (!authsAndRoles.isEmpty()) {
            authsAndRoles = ".auths([" + authsAndRoles + "])";
        }
        return authsAndRoles;
    }
}
