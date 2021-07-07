package net.timxekhach.generator.auths;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.timxekhach.generator.GeneratorCenter;
import net.timxekhach.security.constant.AuthEnum;
import net.timxekhach.security.constant.RoleEnum;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AuthsGenerator {
    public static final String TEMPLATE_PATH = "SourceCode/TimXeKhachSpring/src/main/java/net/timxekhach/generator/auths/ftl-template/";
    public static final String ROLE_TEMPLATE_NAME = "xe.role.ts.ftl";
    public static final String AUTH_TEMPLATE_NAME = "xe.auth.ts.ftl";
    public static final String ROLE_PATH = "SourceCode/XeKhachAngular/src/app/business/xe.role.ts";
    public static final String AUTH_PATH = "SourceCode/XeKhachAngular/src/app/business/auth.enum.ts";

    public static void run() {
        Configuration config = GeneratorCenter.getConfig();
        Map<String, Object> input = new HashMap<>();
        input.put("roles", RoleEnum.values());
        input.put("auths", AuthEnum.values());
        Template roleTemplate = GeneratorCenter.prepareTemplate(config, TEMPLATE_PATH, ROLE_TEMPLATE_NAME);
        GeneratorCenter.writeToFile(input, roleTemplate, new File(ROLE_PATH));
        Template authTemplate = GeneratorCenter.prepareTemplate(config, TEMPLATE_PATH, AUTH_TEMPLATE_NAME);
        GeneratorCenter.writeToFile(input, authTemplate, new File(AUTH_PATH));
    }
}
