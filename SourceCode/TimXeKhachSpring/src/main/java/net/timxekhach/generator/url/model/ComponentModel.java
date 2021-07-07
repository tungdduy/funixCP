package net.timxekhach.generator.url.model;

import lombok.Getter;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.utility.XeAppUtils;

import static net.timxekhach.generator.GeneratorCenter.APP_PAGES_DIR;

@Getter
public class ComponentModel {
    private final String
            key,
            keyChain,
            componentName,
            pathToFramework,
            pathToI18n,
            pathToAbstract,
            dirPath,
            componentPath,
            htmlPath,
            scssPath,
            url;

    public ComponentModel(UrlNode appUrl) {
        this.key = appUrl.getKey();
        this.keyChain = appUrl.buildKeyChain();
        this.componentName = appUrl.getComponentName();
        this.url = appUrl.getUrl();
        int level = appUrl.getAncestors().size();
        this.pathToFramework = XeAppUtils.getPathToFramework(level);
        this.pathToI18n = XeAppUtils.getPathToI18n(level);
        this.pathToAbstract = XeAppUtils.getPathToAbstract(level);
        this.dirPath = APP_PAGES_DIR + appUrl.buildUrlChain() + "/";
        String path = this.dirPath + this.url;
        this.htmlPath = XeAppUtils.getHtmlPath(path);
        this.scssPath = XeAppUtils.getScssPath(path);
        this.componentPath = XeAppUtils.getComponentPath(path);
    }
}
