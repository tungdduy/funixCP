package generator.models;

import generator.GeneratorSetup;
import generator.models.abstracts.AbstractUrlModel;
import generator.models.sub.Authority;
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
