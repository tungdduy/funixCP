package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.url.AbstractApiUrlTemplateBuilder;
import net.timxekhach.generator.sources.SecurityConfigSource;
import net.timxekhach.security.model.UrlNode;

import java.util.ArrayList;
import java.util.List;

import static net.timxekhach.utility.XeFileUtils.readAsString;
import static net.timxekhach.utility.XeStringUtils.fetchSeparator;

public class SecurityConfigFtl extends AbstractApiUrlTemplateBuilder<SecurityConfigSource> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    private final List<SecurityConfigSource.Authority> authorities = new ArrayList<>();
    @Override
    protected void handleSource(SecurityConfigSource source) {
        UrlNode urlNode = source.getUrlNode();
        authorities.add(new SecurityConfigSource.Authority(urlNode.getBuilder().buildUrlChain(), urlNode.getRoles(), urlNode.getAuths()));
        urlNode.getMethods().forEach(method -> {
            authorities.add(new SecurityConfigSource.Authority(method.getBuilder().buildUrlChain(), method.getRoles(), method.getAuths()));
        });
    }

    @Override
    public void prepareRenderFiles() {
        SecurityConfigSource source = this.getRenderFiles().get(0);
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
