package generator.app.renderers;

import generator.app.models.UrlDeclareTsModel;
import generator.app.models.interfaces.AuthorizationConfig;
import generator.app.renderers.abstracts.AbstractTemplateRender;
import generator.urls.UrlNode;
import generator.urls.UrlTypeEnum;
import net.timxekhach.utility.XeFileUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static generator.urls.UrlArchitect.*;
import static generator.app.models.UrlDeclareTsModel.*;


public class UrlDeclareTsFtl extends AbstractTemplateRender<UrlDeclareTsModel> {
    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected void handleSource(UrlDeclareTsModel source) {
        source.setContentBeforeImport(XeFileUtils.readAsString(
                source.getRenderFile().getAbsolutePath()).split(source.getIMPORT_SEPARATOR())[0]);

        apiUrls.forEach(api -> buildRootModel(source.getApiUrls(), api));
        appUrls.forEach(app -> buildRootModel(source.getAppUrls(), app));
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
        processApiMethodUrl(model, urlNode);
        return model;
    }

    private static void processApiMethodUrl(UrlModel model, UrlNode urlNode) {
        if (urlNode.getUrlType() == UrlTypeEnum.API) {
           urlNode.getMethods().forEach(method -> {
               UrlModel child = new UrlModel();
               child.setConfig(buildConfig(method));
               child.setKey(method.getBuilder().buildKey());
               model.getChildren().add(child);
           });
        }
    }

    void buildChildModels(UrlNode parentNode, UrlModel parentModel) {
        parentNode.getChildren().forEach(childNode -> {
            UrlModel childModel = processModel(parentModel.getChildren(), childNode);
            buildChildModels(childNode, childModel);
        });
    }

    static String buildPublicConfig(AuthorizationConfig authConfig) {
        return authConfig.getIsPublic() != null? ".public()" : "";
    }

    static String buildConfig(AuthorizationConfig authConfig) {
        return  "config()"
                + buildPublicConfig(authConfig)
                + buildAuthsRolesConfig(authConfig);
    }

    /**
     * @return string like .auths([r.ROLE_ADMIN, a.USER_READ])
     * must be consistent with the app config
     */
    static String buildAuthsRolesConfig(AuthorizationConfig authConfig) {
        List<String> auths = authConfig.getAuths().stream()
                .map(a -> "a." + a.name())
                .collect(Collectors.toList());
        List<String> roles = authConfig.getRoles().stream()
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