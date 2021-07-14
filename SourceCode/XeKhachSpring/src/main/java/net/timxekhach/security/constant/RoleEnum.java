package net.timxekhach.security.constant;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.timxekhach.security.constant.AuthEnum.*;
@Getter
public enum RoleEnum {
    ROLE_BUSS_ADMIN(ADMIN_READ, ADMIN_WRITE),
    ROLE_BUSS_STAFF(
            Collections.singletonList(ROLE_BUSS_ADMIN),
            USER_WRITE, USER_READ
            ),
    ROLE_CALLER_STAFF(
            Arrays.asList(ROLE_BUSS_ADMIN, ROLE_BUSS_STAFF),
            USER_WRITE, USER_READ, ADMIN_READ
    ),
    ROLE_SYS_ADMIN,
    ROLE_USER;

    private List<AuthEnum> authList = new ArrayList<>();
    private List<RoleEnum> roleList = new ArrayList<>();
    RoleEnum(List<RoleEnum> roleList, AuthEnum... auths) {
        this.authList = Arrays.asList(auths);
        this.roleList = roleList;
    }
    RoleEnum(){}
    RoleEnum(AuthEnum... auths) {
        this.authList = Arrays.asList(auths);
    }

}
