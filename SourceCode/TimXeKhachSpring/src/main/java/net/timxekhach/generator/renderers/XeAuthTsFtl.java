package net.timxekhach.generator.renderers;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.constant.AuthEnum;

import java.util.Collections;
import java.util.List;

import static net.timxekhach.utility.XeAppUtils.BUSINESS_DIR;

public class XeAuthTsFtl<E extends AbstractTemplateSource> extends AbstractTemplateBuilder<E> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<E> prepareRenderFiles() {
        return Collections.singletonList((E) new AbstractTemplateSource() {

            //______________ Must be used in template
            @Getter
            private final AuthEnum[] auths = AuthEnum.values();
            //____________________________________

            @Override
            protected String buildRenderFilePath() {
                return BUSINESS_DIR + "auth.enum.ts";
            }
        });
    }
}
