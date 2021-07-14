package generator;

import generator.urls.UrlArchitect;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.security.constant.RoleEnum;

import java.util.Map;

import static net.timxekhach.security.constant.AuthEnum.*;
import static net.timxekhach.security.constant.RoleEnum.ROLE_BUSS_STAFF;
import static net.timxekhach.security.constant.RoleEnum.ROLE_USER;

public class UrlDeclaration {
    public static void startBuildUrl(){
        UrlArchitect
                .startApi("user").roles(ROLE_USER)
                    .method("login").type(User.class).param("info", Map.class).auths(ADMIN_READ, USER_READ).roles(ROLE_BUSS_STAFF)
                    .method("register").type(User.class).param("user", User.class)
                    .method("forgot-password").param("email", String.class).auths(ADMIN_WRITE)
                .create("admin")
                    .method("list").pathVar("id", Long.class)
                .create("caller-staff")
                .create("buss-staff")
        ;

        UrlArchitect
                .startApp("check-in")
                    .child("login")
                    .sibling("register")
                    .sibling("forgot-password").roles(RoleEnum.ROLE_BUSS_ADMIN).auths(ADMIN_READ)
                .create("admin")
                    .child("buss-staff")
        ;

    }

}
