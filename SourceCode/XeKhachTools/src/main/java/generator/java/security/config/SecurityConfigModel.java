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

    @Override
    public void prepareSeparator() {
        separator("beforeAuthorization").append();
        separator("afterAuthorization").append();
    }
    private List<Authority> authorities = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return GeneratorSetup.API_ROOT + "security/handler/SecurityConfig.java";
    }

}
