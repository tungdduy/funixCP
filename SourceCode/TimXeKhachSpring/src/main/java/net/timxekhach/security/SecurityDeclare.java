package net.timxekhach.security;

import net.timxekhach.operation.entity.User;
import net.timxekhach.security.handler.UrlBuilder;
import net.timxekhach.security.constant.AuthEnum;
import net.timxekhach.security.constant.RoleEnum;

import java.util.Map;

public class SecurityDeclare {
    public static void startBuildUrl(){
        UrlBuilder
                .startApi("user")
                    .method("login").type(User.class).param("info", Map.class)
                    .method("register").type(User.class).param("user", User.class)
                .create("admin")
                    .method("list").pathVar("id", Long.class)
                .create("caller-staff")
                .create("buss-staff")
        ;

        UrlBuilder
                .startApp("check-in")
                    .child("login").roles(RoleEnum.ROLE_BUSS_ADMIN).auths(AuthEnum.ADMIN_READ)
                    .sibling("register")
                    .sibling("forgot-password")
                .create("admin")
                    .child("buss-staff")
        ;

    }

}
