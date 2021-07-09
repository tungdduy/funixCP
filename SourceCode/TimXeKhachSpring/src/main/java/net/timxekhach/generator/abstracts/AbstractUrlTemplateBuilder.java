package net.timxekhach.generator.abstracts;

import net.timxekhach.generator.renderers.ApiMessagesTsFtl;
import net.timxekhach.security.handler.UrlArchitect;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.security.model.UrlTypeEnum.UrlTypeEnum;
import net.timxekhach.utility.XeReflectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("all")
public abstract class AbstractUrlTemplateBuilder<E extends AbstractTemplateSource> extends AbstractTemplateBuilder<E> {

    protected boolean fetchModuleOnly() {return false;};

    protected abstract E visitUrlNode(UrlNode urlNode);

    protected abstract List<UrlTypeEnum> traverseUrlTypes();

    @Override
    protected List<E> prepareRenderFiles() {
        return this.getRenderFiles();
    }

    public static <E extends AbstractTemplateSource> void buildToFiles(){
        String packageName = ApiMessagesTsFtl.class.getPackage().getName();
        List<? extends AbstractUrlTemplateBuilder> builders
                = XeReflectionUtils.newInstancesOfAllChildren(AbstractUrlTemplateBuilder.class, packageName);

        UrlArchitect.traverseAppUrls(visitUrlNodeConsumer(builders));
        UrlArchitect.traverseApiUrls(visitUrlNodeConsumer(builders));

        builders.forEach(AbstractTemplateBuilder::executeRenderFiles);
    }

    @NotNull
    private static Consumer<UrlNode> visitUrlNodeConsumer(List<? extends AbstractUrlTemplateBuilder> builders) {
        return urlNode -> {
            builders.forEach(builder -> {
                if(!builder.traverseUrlTypes().contains(urlNode.getUrlType())) {
                    return;
                }
                if (!builder.fetchModuleOnly()
                        || urlNode.getBuilder().isModule()) {

                    Object source = builder.visitUrlNode(urlNode);

                    if (source != null) {
                        builder.renderFiles.add(source);
                    }
                }
            });
        };
    }


    protected List<E> getRenderFiles(){
        return this.renderFiles;
    }

    final List<E> renderFiles = new ArrayList<>();

    Consumer<UrlNode> fetchSource = urlNode -> {
        if (!fetchModuleOnly()
                || urlNode.getBuilder().isModule()) {
            E source = visitUrlNode(urlNode);
            if(source != null) {
                this.renderFiles.add(source);
            }
        }
    };
}
