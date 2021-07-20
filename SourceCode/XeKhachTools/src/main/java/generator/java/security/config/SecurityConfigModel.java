package generator.java.security.config;

import generator.GeneratorSetup;
import generator.abstracts.models.AbstractUrlModel;
import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SecurityConfigModel extends AbstractUrlModel {

    private String urlAuthorizationSplitter = StringUtil.buildSeparator("AUTHORIZATION");
    private List<Authority> authorities = new ArrayList<>();
    private String contentBeforeAuthorization, contentAfterAuthorization;

    @Override
    public String buildRenderFilePath() {
        return GeneratorSetup.API_ROOT + "security/handler/SecurityConfig.java";
    }

}
