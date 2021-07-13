package generator.app.renderers;

import generator.app.builders.UrlNodeBuilder;
import generator.app.models.UrlImportTsModel;
import generator.app.renderers.abstracts.AbstractAppUrlTemplateRender;
import generator.urls.UrlNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UrlImportTsFtl extends AbstractAppUrlTemplateRender<UrlImportTsModel> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    public void prepareRenderFiles() {
        UrlImportTsModel source = super.getRenderFiles().get(0);
        source.setUrlImports(urlImportsMap.entrySet());
        this.getRenderFiles().clear();
        this.getRenderFiles().add(source);
    }

    private final Map<String, String> urlImportsMap = new HashMap<>();

    @Override
    protected void handleSource(UrlImportTsModel source) {
        logger.info(String.format("handle: %s", source.getUrlNode().getBuilder().buildUrlChain()));
        UrlNode urlNode = source.getUrlNode();
        UrlNodeBuilder builder = urlNode.getBuilder();
        String key = builder.buildKeyChain() + "-component";
        String value = String.format("require('%s.component').%s",
                prefixImport(urlNode),
                builder.buildComponentName());
        urlImportsMap.put(key, value);
        if (urlNode.getChildren().size() > 0) {
            key = builder.buildKeyChain() + "-module";
            value = "import('" + prefixImport(urlNode) + ".module').then(m => m." + builder.buildModuleName() + ")";
            urlImportsMap.put(key, value);
        }
    }

    private static String prefixImport(UrlNode url) {
        return "../../business/pages/" + url.getBuilder().buildUrlChain() + "/" + url.getUrl();
    }

}
