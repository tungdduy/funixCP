package generator.app.renderers.abstracts;

import generator.app.models.abstracts.AbstractTemplateModel;
import generator.app.models.abstracts.AbstractUrlTemplateModel;
import generator.app.renderers.ApiMessagesTsFtl;
import generator.DeclarationCentral;
import generator.urls.UrlArchitect;
import generator.urls.UrlNode;
import generator.urls.UrlTypeEnum;
import net.timxekhach.utility.XeReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("all")
public abstract class AbstractUrlTemplateRender<E extends AbstractUrlTemplateModel> extends AbstractTemplateRender<E> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    protected boolean fetchModuleOnly() {return false;};
    protected abstract List<UrlTypeEnum> traverseUrlTypes();

    void visit(UrlNode urlNode) {
        if(!this.traverseUrlTypes().contains(urlNode.getUrlType())) {
            this.logger.warn(String.format("traverse cancelled, url-type: %s, this type: %s", urlNode.getUrlType(), this.traverseUrlTypes()));
            return;
        }
        if(this.fetchModuleOnly() && !urlNode.getBuilder().isModule()) {
            this.logger.warn(String.format("traverse cancelled: %s is not a routing module", urlNode.getBuilder().buildUrlChain()));
            return;
        }

        try {
            E source = newSource();
            source.setUrlNode(urlNode);
            this.getRenderFiles().add(source);
            this.handleSource(source);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected boolean needHandleSourceOnInit() {
        return false;
    }

    @Override
    protected boolean isBuildFileManually() {
        return true;
    }

    public static <E extends AbstractTemplateModel> void buildUrlFiles(){
        DeclarationCentral.startBuildUrl();
        String packageName = ApiMessagesTsFtl.class.getPackage().getName();
        List<? extends AbstractUrlTemplateRender> builders
                = XeReflectionUtils.newInstancesOfAllChildren(AbstractUrlTemplateRender.class, packageName);
        Consumer<UrlNode> visitNode = node -> builders.forEach(builder -> builder.visit(node));
        UrlArchitect.traverseAppUrls(visitNode);
        UrlArchitect.traverseApiUrls(visitNode);
        builders.forEach(AbstractTemplateRender::executeRenderFiles);
    }

}
