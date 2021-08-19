package generator.abstracts.render;

import architect.UrlDeclaration;
import architect.urls.UrlArchitect;
import architect.urls.UrlNode;
import architect.urls.UrlTypeEnum;
import generator.abstracts.models.AbstractUrlModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("all")
public abstract class AbstractUrlRender<E extends AbstractUrlModel> extends AbstractRender<E> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    protected boolean fetchModuleOnly() {return false;};
    protected abstract List<UrlTypeEnum> traverseUrlTypes();

    void visit(UrlNode urlNode) {
        if(!this.traverseUrlTypes().contains(urlNode.getUrlType())) {
            this.logger.debug(String.format("traverse cancelled, url-type: %s, this type: %s", urlNode.getUrlType(), this.traverseUrlTypes()));
            return;
        }
        if(this.fetchModuleOnly() && !urlNode.getBuilder().isModule()) {
            this.logger.debug(String.format("traverse cancelled: %s is not a routing module", urlNode.getBuilder().buildUrlChain()));
            return;
        }

        try {
            AbstractUrlModel.urlNodeHolder.value = urlNode;
            E model = newModel();
            model.setUrlNode(urlNode);
            this.getModelFiles().add(model);
            this.handleModel(model);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected boolean isNewModelOnInit() {
        return false;
    }

    @Override
    protected boolean isManualRender() {
        return true;
    }

    public AbstractUrlRender() {
        renders.add(this);
    }

    private static List<AbstractUrlRender> renders = new ArrayList<>();

    public static void renderWithParent(){
        UrlDeclaration.startBuildUrl();
        Consumer<UrlNode> visitNode = node -> renders.forEach(render -> render.visit(node));
        UrlArchitect.traverseAppUrls(visitNode);
        UrlArchitect.traverseApiUrls(visitNode);
        renders.forEach(AbstractRender::executeRenders);
    }

    @Override
    public void singleRender() {
        AbstractUrlRender.renderWithParent();
    }

    public static void standaloneRender() {
        batchNewAllChildrenRenders(AbstractUrlRender.class);
        renderWithParent();
    }

}
