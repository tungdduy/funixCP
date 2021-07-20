package generator.java.security.config;

import architect.urls.UrlNode;
import generator.abstracts.render.AbstractApiUrlRender;

import java.util.ArrayList;
import java.util.List;

import static net.timxekhach.utility.XeFileUtils.readAsString;
import static util.StringUtil.fetchSplitter;

public class SecurityConfigRender extends AbstractApiUrlRender<SecurityConfigModel> {

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
        String oldContent = readAsString(source.buildRenderFilePath());
        String[] separatorContent = fetchSplitter(source.getUrlAuthorizationSplitter(), oldContent);
        if (separatorContent != null) {
            source.setContentBeforeAuthorization(separatorContent[0]);
            source.setContentAfterAuthorization(separatorContent[2]);
        }
        source.setAuthorities(authorities);
        this.getModelFiles().clear();
        this.getModelFiles().add(source);
    }

}
