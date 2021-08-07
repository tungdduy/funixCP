package generator.ts.url.imports;

import architect.urls.UrlNode;
import generator.abstracts.render.AbstractAppUrlRender;
import generator.builders.UrlNodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UrlImportTsRender extends AbstractAppUrlRender<UrlImportTsModel> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    public void runBeforeRender() {
        UrlImportTsModel source = super.getModelFiles().get(0);
        source.setUrlImports(urlImportsMap.entrySet());
        this.getModelFiles().clear();
        this.getModelFiles().add(source);
    }

    private final Map<String, String> urlImportsMap = new HashMap<>();

    @Override
    protected void handleModel(UrlImportTsModel model) {
        logger.debug(String.format("handle: %s", model.getUrlNode().getBuilder().buildUrlChain()));
        UrlNode urlNode = model.getUrlNode();
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
        return "app/business/pages/" + url.getBuilder().buildUrlChain() + "/" + url.getUrl();
    }

}
