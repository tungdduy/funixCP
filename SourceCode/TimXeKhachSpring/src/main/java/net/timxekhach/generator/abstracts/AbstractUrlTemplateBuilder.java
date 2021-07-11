package net.timxekhach.generator.abstracts;

import net.timxekhach.generator.renderers.ApiMessagesTsFtl;
import net.timxekhach.security.SecurityDeclare;
import net.timxekhach.security.handler.UrlArchitect;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.security.model.UrlTypeEnum;
import net.timxekhach.utility.XeReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("all")
public abstract class AbstractUrlTemplateBuilder<E extends AbstractUrlTemplateSource> extends AbstractTemplateBuilder<E> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

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

    public static <E extends AbstractTemplateSource> void buildUrlFiles(){
        SecurityDeclare.startBuildUrl();
        String packageName = ApiMessagesTsFtl.class.getPackage().getName();
        List<? extends AbstractUrlTemplateBuilder> builders
                = XeReflectionUtils.newInstancesOfAllChildren(AbstractUrlTemplateBuilder.class, packageName);
        Consumer<UrlNode> visitNode = node -> builders.forEach(builder -> builder.visit(node));
        UrlArchitect.traverseAppUrls(visitNode);
        UrlArchitect.traverseApiUrls(visitNode);
        builders.forEach(AbstractTemplateBuilder::executeRenderFiles);
    }

}
