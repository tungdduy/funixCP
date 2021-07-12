package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.constant.AuthEnum;

import static net.timxekhach.utility.XeAppUtil.BUSINESS_DIR;

@Getter @Setter
public class XeAuthTsSource extends AbstractTemplateSource {

    private final AuthEnum[] auths = AuthEnum.values();

    @Override
    public String buildRenderFilePath() {
        return BUSINESS_DIR + "auth.enum.ts";
    }

}
