package generator.renders.abstracts;

import generator.models.abstracts.AbstractTemplateModel;
import generator.models.abstracts.AbstractUrlTemplateModel;
import generator.renders.ApiMessagesTsRender;
import architect.UrlDeclaration;
import architect.urls.UrlArchitect;
import architect.urls.UrlNode;
import architect.urls.UrlTypeEnum;
import util.ReflectionUtil;
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
            E source = newModel();
            source.setUrlNode(urlNode);
            this.getRenderFiles().add(source);
            this.handleModel(source);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected boolean isNewModelOnInit() {
        return false;
    }

    @Override
    protected boolean isBuildFileManually() {
        return true;
    }

    public static <E extends AbstractTemplateModel> void buildUrlFiles(){
        UrlDeclaration.startBuildUrl();
        String packageName = ApiMessagesTsRender.class.getPackage().getName();
        List<? extends AbstractUrlTemplateRender> builders
                = ReflectionUtil.newInstancesOfAllChildren(AbstractUrlTemplateRender.class, packageName);
        Consumer<UrlNode> visitNode = node -> builders.forEach(builder -> builder.visit(node));
        UrlArchitect.traverseAppUrls(visitNode);
        UrlArchitect.traverseApiUrls(visitNode);
        builders.forEach(AbstractTemplateRender::executeRenderFiles);
    }

}
