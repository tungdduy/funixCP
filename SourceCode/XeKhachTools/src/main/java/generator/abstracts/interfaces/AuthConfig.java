package generator.abstracts.interfaces;

import util.constants.RoleEnum;

import java.util.List;

public interface AuthConfig {
    List<RoleEnum> getRoles();
    Boolean getIsPublic();
}
