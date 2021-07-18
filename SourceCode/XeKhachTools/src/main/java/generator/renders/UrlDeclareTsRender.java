package generator.renders;

import architect.UrlDeclaration;
import architect.urls.UrlArchitect;
import generator.models.UrlDeclareTsModel;
import generator.models.interfaces.AuthConfig;
import generator.models.sub.Url;
import generator.renders.abstracts.AbstractRender;
import architect.urls.UrlNode;
import architect.urls.UrlTypeEnum;
import net.timxekhach.utility.XeFileUtils;

import java.util.List;
import java.util.stream.Collectors;

import static architect.urls.UrlArchitect.*;


public class UrlDeclareTsRender extends AbstractRender<UrlDeclareTsModel> {
    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected void handleModel(UrlDeclareTsModel model) {
        UrlDeclaration.startBuildUrl();
        model.setContentBeforeImport(XeFileUtils.readAsString(
                model.getRenderFile().getAbsolutePath()).split(model.getIMPORT_SPLITTER())[0]);

        apiUrls.forEach(api -> buildRootModel(model.getApiUrls(), api));
        appUrls.forEach(app -> buildRootModel(model.getAppUrls(), app));
    }

    void buildRootModel(List<Url> models, UrlNode urlNode) {
        Url rootModel = processModel(models, urlNode);
        buildChildModels(urlNode, rootModel);
    }

    private static Url processModel(List<Url> models, UrlNode urlNode) {
        Url model = new Url();
        model.setConfig(buildConfig(urlNode));
        model.setKey(urlNode.getBuilder().buildKey());
        models.add(model);
        processApiMethodUrl(model, urlNode);
        return model;
    }

    private static void processApiMethodUrl(Url model, UrlNode urlNode) {
        if (urlNode.getUrlType() == UrlTypeEnum.API) {
           urlNode.getMethods().forEach(method -> {
               Url child = new Url();
               child.setConfig(buildConfig(method));
               child.setKey(method.getBuilder().buildKey());
               model.getChildren().add(child);
           });
        }
    }

    void buildChildModels(UrlNode parentNode, Url parentModel) {
        parentNode.getChildren().forEach(childNode -> {
            Url childModel = processModel(parentModel.getChildren(), childNode);
            buildChildModels(childNode, childModel);
        });
    }

    static String buildPublicConfig(AuthConfig authConfig) {
        return authConfig.getIsPublic() != null? ".public()" : "";
    }

    static String buildConfig(AuthConfig authConfig) {
        return  "config()"
                + buildPublicConfig(authConfig)
                + buildAuthsRolesConfig(authConfig);
    }

    /**
     * @return string like .auths([r.ROLE_ADMIN, a.USER_READ])
     * must be consistent with the app config
     */
    static String buildAuthsRolesConfig(AuthConfig authConfig) {
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
