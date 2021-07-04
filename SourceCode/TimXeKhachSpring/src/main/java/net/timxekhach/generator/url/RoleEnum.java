package net.timxekhach.generator.url;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.timxekhach.generator.url.AuthEnum.*;

public enum RoleEnum {
    ROLE_BUSS_ADMIN(ADMIN_READ, ADMIN_WRITE),
    ROLE_BUSS_STAFF(
            Arrays.asList(ROLE_BUSS_ADMIN),
            USER_WRITE, USER_READ
            ),
    ROLE_CALLER_STAFF,
    ROLE_SYS_ADMIN,
    ROLE_USER;

    List<AuthEnum> authList = new ArrayList<>();
    List<RoleEnum> roleList = new ArrayList<>();
    RoleEnum(List<RoleEnum> roleList, AuthEnum... auths) {
        this.authList = Arrays.asList(auths);
        this.roleList = roleList;
    }
    RoleEnum(){}
    RoleEnum(AuthEnum... auths) {
        this.authList = Arrays.asList(auths);
    }

}
