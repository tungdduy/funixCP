package generator.ts.module.routing;

import generator.abstracts.render.AbstractAppUrlRender;
import util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class RoutingModuleTsRender extends AbstractAppUrlRender<RoutingModuleTsModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean fetchModuleOnly() {
        return true;
    }

    @Override
    protected void handleModel(RoutingModuleTsModel model) {
        int level = model.getUrlNode().getBuilder().getLevel();
        model.setPathToFramework(AppUtil.getPathToFramework(level));
        model.setUrlKeyChain(model.getUrlNode().getBuilder().buildKeyChain());
        model.setCapitalizeName(model.getUrlNode().getBuilder().buildCapitalizeName());
        model.setUrl(model.getUrlNode().getUrl());

        List<RoutingModuleTsModel.Component> components = new ArrayList<>();
        List<RoutingModuleTsModel.Module> modules = new ArrayList<>();
        model.getUrlNode().getChildren().forEach(child -> {
            createComponentThenAdd(components, child);
            createModuleThenAdd(child);
        });
        model.setComponents(components);
        model.setModules(modules);
    }

    private void createModuleThenAdd(architect.urls.UrlNode child) {
        if (!child.getBuilder().isModule()) return;
        RoutingModuleTsModel.Module module = new RoutingModuleTsModel.Module();
        module.setName(child.getBuilder().buildComponentName());
        module.setPath(child.getUrl());
    }

    private void createComponentThenAdd(List<RoutingModuleTsModel.Component> components, architect.urls.UrlNode child) {
        if(child.getBuilder().isModule()) return;
        RoutingModuleTsModel.Component component = new RoutingModuleTsModel.Component();
        component.setName(child.getBuilder().buildComponentName());
        component.setPath(child.getUrl());
        if(child.getRoles().size() > 0) {
            component.setCanActivate(child.getBuilder().buildKeyChain());
        }
        components.add(component);
    }
}
