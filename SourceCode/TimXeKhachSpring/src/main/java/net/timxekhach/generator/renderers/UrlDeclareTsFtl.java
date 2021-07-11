package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractTemplateBuilder;
import net.timxekhach.generator.sources.UrlDeclareTsSource;
import net.timxekhach.security.handler.UrlArchitect;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.utility.XeFileUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static net.timxekhach.security.handler.UrlArchitect.apiUrls;
import static net.timxekhach.generator.sources.UrlDeclareTsSource.UrlModel;

public class UrlDeclareTsFtl extends AbstractTemplateBuilder<UrlDeclareTsSource> {
    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected void handleSource(UrlDeclareTsSource source) {
        source.setContentBeforeImport(XeFileUtils.readAsString(
                source.getRenderFile().getAbsolutePath()).split(source.getIMPORT_SEPARATOR())[0]);

        apiUrls.forEach(api -> buildRootModel(source.getApiUrls(), api));
        UrlArchitect.appUrls.forEach(app -> buildRootModel(source.getAppUrls(), app));
    }

    void buildRootModel(List<UrlModel> models, UrlNode urlNode) {
        UrlModel rootModel = processModel(models, urlNode);
        buildChildModels(urlNode, rootModel);
    }

    @NotNull
    private static UrlModel processModel(List<UrlModel> models, UrlNode urlNode) {
        UrlModel model = new UrlModel();
        model.setConfig(buildConfig(urlNode));
        model.setKey(urlNode.getBuilder().buildKey());
        models.add(model);
        return model;
    }

    void buildChildModels(UrlNode parentNode, UrlModel parentModel) {
        parentNode.getChildren().forEach(childNode -> {
            UrlModel childModel = processModel(parentModel.getChildren(), childNode);
            buildChildModels(childNode, childModel);
        });
    }

    static String buildPublicConfig(UrlNode url) {
        return url.getIsPublic() != null? ".public()" : "";
    }

    static String buildConfig(UrlNode url) {
        return  "config()"
                + buildPublicConfig(url)
                + buildAuthsRolesConfig(url);
    }

    /**
     * @return string like .auths([r.ROLE_ADMIN, a.USER_READ])
     * must be consistent with the app config
     */
    static String buildAuthsRolesConfig(UrlNode url) {
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
