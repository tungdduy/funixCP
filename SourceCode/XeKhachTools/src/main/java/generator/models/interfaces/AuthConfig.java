package generator.models.interfaces;

import net.timxekhach.security.constant.AuthEnum;
import net.timxekhach.security.constant.RoleEnum;

import java.util.List;

public interface AuthConfig {
    List<AuthEnum> getAuths();
    List<RoleEnum> getRoles();
    Boolean getIsPublic();
}
