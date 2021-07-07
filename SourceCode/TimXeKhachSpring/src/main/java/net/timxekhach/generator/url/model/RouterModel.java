package net.timxekhach.generator.url.model;

import lombok.Getter;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.utility.XeAppUtils;

import java.util.ArrayList;
import java.util.List;

import static net.timxekhach.generator.GeneratorCenter.APP_PAGES_DIR;

@Getter
public class RouterModel {

    public RouterModel(UrlNode appUrl) {
        int level = appUrl.getAncestors().size();
        this.pathToFramework = XeAppUtils.getPathToFramework(level);
        this.pathToI18n = XeAppUtils.getPathToI18n(level);
        this.urlKeyChain =  appUrl.buildKeyChain();
        this.capitalizeName = appUrl.getCapitalizedName();
        this.url = appUrl.getUrl();
        this.dirPath = APP_PAGES_DIR + appUrl.buildUrlChain() + "/";
        String path = this.dirPath + this.url;
        this.routingModulePath = path + "-routing.module.ts";
        this.modulePath = path + ".module.ts";
        this.routerComponentPath = XeAppUtils.getComponentPath(path);
        this.htmlPath = XeAppUtils.getHtmlPath(path);
        this.scssPath = XeAppUtils.getScssPath(path);
        this.buildRouterChildren(appUrl);
    }

    private final List<ComponentModel> children = new ArrayList<>();
    private final String
            url,
            dirPath,
            pathToFramework,
            urlKeyChain,
            capitalizeName,
            pathToI18n,
            routingModulePath,
            routerComponentPath,
            htmlPath,
            scssPath,
            modulePath;

    void buildRouterChildren(UrlNode appUrl) {
        appUrl.getChildren().forEach(child -> {
            this.getChildren().add(new ComponentModel(child));
        });
    }

}
