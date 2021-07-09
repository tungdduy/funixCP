package net.timxekhach.generator.renderers;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.handler.UrlArchitect;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.utility.XeFileUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.timxekhach.utility.XeAppUtils.APP_ROOT;

public class UrlDeclareTsFtl extends AbstractTemplateBuilder<UrlDeclareTsFtl.TemplateSource> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected List<TemplateSource> prepareRenderFiles() {
        return Collections.singletonList(new TemplateSource());
    }

    @Getter
    public static class TemplateSource extends AbstractTemplateSource {

        //______________ Must be used in template
        private String contentBeforeImport;
        private final String IMPORT_SEPARATOR = "// ================= IMPORT TO END OF FILE =================== //";
        private final List<UrlModel> apiUrls = new ArrayList<>();
        private final List<UrlModel> appUrls = new ArrayList<>();
        //__________________________________________________________

        @Override
        protected void init() {
            contentBeforeImport = XeFileUtil.readAsString(
                    renderFile.getAbsolutePath()).split(IMPORT_SEPARATOR)[0];

            UrlArchitect.apiUrls.forEach(api -> buildRootModel(apiUrls, api));
            UrlArchitect.appUrls.forEach(app -> buildRootModel(appUrls, app));
        }

        @Override
        protected String buildRenderFilePath() {
            return APP_ROOT + "framework/url/url.declare.ts";
        }

        static void buildRootModel(List<UrlModel> models, UrlNode urlNode) {
            UrlModel model = new UrlModel(urlNode);
            models.add(model);
            buildChildModels(urlNode, model);
        }

        static void buildChildModels(UrlNode parentNode, UrlModel parentModel) {
            parentNode.getChildren().forEach(childNode -> {
                UrlModel childModel = new UrlModel(childNode);
                parentModel.getChildren().add(childModel);
                buildChildModels(childNode, childModel);
            });
        }
    }

    @Getter
    public static class UrlModel {
        private final String config;
        private final String key;
        private final List<UrlModel> children = new ArrayList<>();
        public UrlModel(UrlNode node) {
            this.config = buildConfig(node);
            this.key = node.getBuilder().buildKey();
        }

        private static String buildPublicConfig(UrlNode url) {
            return url.getIsPublic() != null? ".public()" : "";
        }

        private static String buildConfig(UrlNode url) {
            return  "config()"
                    + buildPublicConfig(url)
                    + buildAuthsRolesConfig(url);
        }

        /**
         * @return string like .auths([r.ROLE_ADMIN, a.USER_READ])
         * must be consistent with the app config
         */
        private static String buildAuthsRolesConfig(UrlNode url) {
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


}
