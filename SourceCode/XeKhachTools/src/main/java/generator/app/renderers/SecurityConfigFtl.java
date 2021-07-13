package generator.app.renderers;

import generator.app.models.SecurityConfigModel;
import generator.app.renderers.abstracts.AbstractApiUrlTemplateRender;
import generator.urls.UrlNode;

import java.util.ArrayList;
import java.util.List;

import static net.timxekhach.utility.XeFileUtils.readAsString;
import static net.timxekhach.utility.XeStringUtils.fetchSeparator;

public class SecurityConfigFtl extends AbstractApiUrlTemplateRender<SecurityConfigModel> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    private final List<SecurityConfigModel.Authority> authorities = new ArrayList<>();
    @Override
    protected void handleSource(SecurityConfigModel source) {
        UrlNode urlNode = source.getUrlNode();
        authorities.add(new SecurityConfigModel.Authority(urlNode.getBuilder().buildUrlChain(), urlNode.getRoles(), urlNode.getAuths()));
        urlNode.getMethods().forEach(method -> {
            authorities.add(new SecurityConfigModel.Authority(method.getBuilder().buildUrlChain(), method.getRoles(), method.getAuths()));
        });
    }

    @Override
    public void prepareRenderFiles() {
        SecurityConfigModel source = this.getRenderFiles().get(0);
        String oldContent = readAsString(source.buildRenderFilePath());
        String[] separatorContent = fetchSeparator(source.getUrlAuthorizationSeparator(), oldContent);
        if (separatorContent != null) {
            source.setContentBeforeAuthorization(separatorContent[0]);
            source.setContentAfterAuthorization(separatorContent[2]);
        }
        source.setAuthorities(authorities);
        this.getRenderFiles().clear();
        this.getRenderFiles().add(source);
    }

}
