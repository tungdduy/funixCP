package net.timxekhach.generator.sources;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.generator.GeneratorCenter;
import net.timxekhach.generator.abstracts.url.AbstractUrlTemplateSource;
import net.timxekhach.security.constant.AuthEnum;
import net.timxekhach.security.constant.RoleEnum;
import net.timxekhach.utility.XeStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class SecurityConfigSource extends AbstractUrlTemplateSource {

    private String urlAuthorizationSeparator = XeStringUtils.buildSeparator("AUTHORIZATION");
    private List<Authority> authorities = new ArrayList<>();
    private String contentBeforeAuthorization = "", contentAfterAuthorization = "";

    @Override
    public String buildRenderFilePath() {
        return GeneratorCenter.API_ROOT + "security/handler/SecurityConfig.java";
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
                        .map(XeStringUtils::wrapInQuote)
                        .collect(Collectors.joining(", "))
                );
            } else {
                authorities = String.format(".hasAnyAuthority(%s)",
                        XeStringUtils.joinAsArguments(
                            roles.stream().map(RoleEnum::name).map(XeStringUtils::wrapInQuote).collect(Collectors.joining(", ")),
                            auths.stream().map(AuthEnum::name).map(XeStringUtils::wrapInQuote).collect(Collectors.joining(", "))
                ));
            }
        }
    }
}
