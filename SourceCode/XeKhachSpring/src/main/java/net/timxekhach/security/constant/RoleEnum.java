package net.timxekhach.security.constant;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum RoleEnum {
    ROLE_USER,
    ROLE_BUSS_STAFF,
    ROLE_CALLER_STAFF,
    ROLE_BUSS_ADMIN(ROLE_BUSS_STAFF, ROLE_CALLER_STAFF),
    ROLE_SYS_ADMIN
    ;

    private List<RoleEnum> roleList = new ArrayList<>();
    RoleEnum(RoleEnum... roles) {
        this.roleList = Arrays.asList(roles);
    }
    RoleEnum(){}

}
