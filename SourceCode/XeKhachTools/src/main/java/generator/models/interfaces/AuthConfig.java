package generator.models.interfaces;

import net.timxekhach.security.constant.RoleEnum;

import java.util.List;

public interface AuthConfig {
    List<RoleEnum> getRoles();
    Boolean getIsPublic();
}
