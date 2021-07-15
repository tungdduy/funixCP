package generator.models;

import generator.GeneratorSetup;
import generator.models.abstracts.AbstractUrlTemplateModel;
import generator.models.sub.Authority;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.security.constant.AuthEnum;
import net.timxekhach.security.constant.RoleEnum;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class SecurityConfigModel extends AbstractUrlTemplateModel {

    private String urlAuthorizationSeparator = StringUtil.buildSeparator("AUTHORIZATION");
    private List<Authority> authorities = new ArrayList<>();
    private String contentBeforeAuthorization = "", contentAfterAuthorization = "";

    @Override
    public String buildRenderFilePath() {
        return GeneratorSetup.API_ROOT + "security/handler/SecurityConfig.java";
    }

}
