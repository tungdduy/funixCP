package net.timxekhach.generator.sources;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.security.constant.RoleEnum;

import static net.timxekhach.utility.XeAppUtil.BUSINESS_DIR;

@Getter
public class XeRoleTsSource extends AbstractTemplateSource {

    private final RoleEnum[] roles = RoleEnum.values();

    @Override
    public String buildRenderFilePath() {
        return BUSINESS_DIR + "xe.role.ts";
    }
}
