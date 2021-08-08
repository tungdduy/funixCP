package generator.java.security.config;

import lombok.Getter;
import lombok.Setter;
import util.constants.RoleEnum;
import util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class Authority {
    private final String url, authorities;

    public Authority(String url, List<RoleEnum> roles) {
        this.url = "/" + url + "/**";
        if(roles.isEmpty()) {
            authorities = ".permitAll()";
        } else {
            authorities = String.format(".hasAnyAuthority(%s)",
                    roles.stream().map(RoleEnum::name).map(StringUtil::wrapInQuote).collect(Collectors.joining(", ")));
        }
    }
}