package generator.app.models;

import generator.GeneratorSetup;
import generator.app.models.abstracts.AbstractUrlTemplateModel;
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

    @Getter
    public static class Authority {
        private final String url, authorities;

        public Authority(String url, List<RoleEnum> roles, List<AuthEnum> auths) {
            this.url = url;
            if (auths.isEmpty()) {
                authorities = String.format(".hasAnyRole(%s)", roles.stream()
                        .map(RoleEnum::name)
                        .map(s -> s.substring("ROLE_".length()))
                        .map(StringUtil::wrapInQuote)
                        .collect(Collectors.joining(", "))
                );
            } else {
                authorities = String.format(".hasAnyAuthority(%s)",
                        StringUtil.joinAsArguments(
                            roles.stream().map(RoleEnum::name).map(StringUtil::wrapInQuote).collect(Collectors.joining(", ")),
                            auths.stream().map(AuthEnum::name).map(StringUtil::wrapInQuote).collect(Collectors.joining(", "))
                ));
            }
        }
    }
}
