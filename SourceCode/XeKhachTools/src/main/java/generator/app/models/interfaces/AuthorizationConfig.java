package generator.app.models.interfaces;

import net.timxekhach.security.constant.AuthEnum;
import net.timxekhach.security.constant.RoleEnum;

import java.util.List;

public interface AuthorizationConfig {
    List<AuthEnum> getAuths();
    List<RoleEnum> getRoles();
    Boolean getIsPublic();
}
