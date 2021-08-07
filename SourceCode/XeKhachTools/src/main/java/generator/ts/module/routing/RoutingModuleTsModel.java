package generator.ts.module.routing;

import generator.abstracts.models.AbstractUrlModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static util.AppUtil.PAGES_DIR;

@Getter
@Setter
public class RoutingModuleTsModel extends AbstractUrlModel {

    private String url, pathToFramework, urlKeyChain, capName;
    private List<Component> components;
    private List<Module> modules;

    @Getter @Setter
    public static class Component {
        String path, name, canActivate;
    }

    @Getter @Setter
    public static class Module {
        String path, name;
    }

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + "-routing.module.ts";
    }
}
