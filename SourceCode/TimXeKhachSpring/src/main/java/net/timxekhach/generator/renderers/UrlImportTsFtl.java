package net.timxekhach.generator.renderers;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractAppUrlTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.generator.url.UrlNodeBuilder;
import net.timxekhach.security.model.UrlNode;

import java.util.*;

import static net.timxekhach.utility.XeAppUtils.FRAMEWORK_URL_DIR;
import static net.timxekhach.utility.XeAppUtils.PAGES_DIR;

public class UrlImportTsFtl<E extends AbstractTemplateSource> extends AbstractAppUrlTemplateBuilder<E> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    private final Map<String, String> urlImportsMap = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    protected List<E> getRenderFiles() {
        return Collections.singletonList((E) new AbstractTemplateSource() {

            @Getter
            private final Set<Map.Entry<String, String>> urlImports = urlImportsMap.entrySet();

            @Override
            protected void init() {
            }

            @Override
            protected String buildRenderFilePath() {
                return FRAMEWORK_URL_DIR + "url.import.ts";
            }
        });
    }

    @Override
    protected E visitUrlNode(UrlNode urlNode) {
        UrlNodeBuilder builder = urlNode.getBuilder();
        String key = builder.buildKeyChain() + "-component";
        String value = "require('" + importPrefix(urlNode) + ".component')." + builder.buildComponentName();
        urlImportsMap.put(key, value);
        if (urlNode.getChildren().size() > 0) {
            key = builder.buildKeyChain() + "-module";
            value = "import('" + importPrefix(urlNode) + ".module').then(m => m." + builder.buildModuleName() + ")";
            urlImportsMap.put(key, value);
        }
        return null;
    }

    private static String importPrefix(UrlNode url) {
        return PAGES_DIR + url.getBuilder().buildUrlChain() + "/" + url.getUrl();
    }

}
