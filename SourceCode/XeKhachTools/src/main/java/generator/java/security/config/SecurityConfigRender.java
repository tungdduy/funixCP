package generator.java.security.config;

import architect.urls.UrlNode;
import generator.abstracts.render.AbstractApiUrlRender;

import java.util.ArrayList;
import java.util.List;

public class SecurityConfigRender extends AbstractApiUrlRender<SecurityConfigModel> {

    public static void main(String[] args) {
        new SecurityConfigRender().singleRender();
    }

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    private final List<Authority> authorities = new ArrayList<>();
    @Override
    protected void handleModel(SecurityConfigModel model) {
        UrlNode urlNode = model.getUrlNode();
        authorities.add(new Authority(urlNode.getBuilder().buildUrlChain(), urlNode.getRoles()));
        urlNode.getMethods().forEach(method -> {
            authorities.add(new Authority(method.getBuilder().buildUrlChain(), method.getRoles()));
        });
    }

    @Override
    public void runBeforeRender() {
        SecurityConfigModel source = this.getModelFiles().get(0);
        source.setAuthorities(authorities);
        this.getModelFiles().clear();
        this.getModelFiles().add(source);
    }

}
