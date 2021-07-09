package net.timxekhach.security.constant;

import java.util.*;

import static net.timxekhach.security.constant.AuthEnum.*;

public enum RoleEnum {
    ROLE_BUSS_ADMIN(ADMIN_READ, ADMIN_WRITE),
    ROLE_BUSS_STAFF(
            Arrays.asList(ROLE_BUSS_ADMIN),
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

    public List<AuthEnum> getAllAuthorities() {
        Set<AuthEnum> auths = new HashSet<>(this.authList);
        fetchAuths(this.roleList, auths);
        return new ArrayList<>(auths);
    }

    private void fetchAuths(List<RoleEnum> roleList, Set<AuthEnum> auths){
        roleList.forEach(role -> {
            auths.addAll(role.authList);
            fetchAuths(role.roleList, auths);
        });
    }

}
