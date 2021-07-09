package net.timxekhach.security;

import net.timxekhach.operation.entity.User;
import net.timxekhach.security.handler.UrlArchitect;
import net.timxekhach.security.constant.AuthEnum;
import net.timxekhach.security.constant.RoleEnum;

import java.util.Map;

public class SecurityDeclare {
    public static void startBuildUrl(){
        UrlArchitect
                .startApi("user")
                    .method("login").type(User.class).param("info", Map.class)
                    .method("register").type(User.class).param("user", User.class)
                    .method("forgot-password").param("email", String.class)
                .create("admin")
                    .method("list").pathVar("id", Long.class)
                .create("caller-staff")
                .create("buss-staff")
        ;

        UrlArchitect
                .startApp("check-in")
                    .child("login")
                    .sibling("register")
                    .sibling("forgot-password").roles(RoleEnum.ROLE_BUSS_ADMIN).auths(AuthEnum.ADMIN_READ)
                .create("admin")
                    .child("buss-staff")
        ;

    }

}
