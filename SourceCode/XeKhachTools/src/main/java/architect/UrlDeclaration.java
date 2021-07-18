package architect;

import architect.urls.UrlArchitect;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.security.constant.RoleEnum;

import java.util.Map;

import static net.timxekhach.security.constant.AuthEnum.*;
import static net.timxekhach.security.constant.RoleEnum.ROLE_BUSS_STAFF;
import static net.timxekhach.security.constant.RoleEnum.ROLE_USER;

public class UrlDeclaration {
    public static void startBuildUrl(){
        if(!UrlArchitect.apiUrls.isEmpty()) return;
        UrlArchitect
                .startApi("user").roles(ROLE_USER)
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
                    .sibling("forgot-password")
                .create("admin")
                    .child("buss-staff")
        ;

    }

}
