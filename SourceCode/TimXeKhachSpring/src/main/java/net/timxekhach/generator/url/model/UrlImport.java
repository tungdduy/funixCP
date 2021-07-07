package net.timxekhach.generator.url.model;

import lombok.Getter;
import net.timxekhach.security.model.UrlNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class UrlImport {
    private String key;
    private String content;

    public static List<UrlImport> build(List<UrlImport> urlImports, List<UrlNode> urlBuilders){
        urlBuilders.forEach(url -> {
            UrlImport component = new UrlImport();
            component.key = url.buildKeyChain() + "-component";
            component.content = "require('" + importPrefix(url) + ".component')." + url.getComponentName();
            urlImports.add(component);
            if (url.getChildren().size() > 0) {
                UrlImport module = new UrlImport();
                module.key = url.buildKeyChain() + "-module";
                module.content = "import('" + importPrefix(url) + ".module').then(m => m." + url.getModuleName() + ")";
                urlImports.add(module);
                build(urlImports, url.getChildren());
            }
        });
        return urlImports;
    }


    @NotNull
    private static String importPrefix(UrlNode url) {
        return "app/business/pages/" + url.buildUrlChain() + "/" + url.getUrl();
    }
}
