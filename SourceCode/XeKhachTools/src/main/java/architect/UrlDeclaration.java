package architect;

import architect.urls.UrlArchitect;
import net.timxekhach.operation.data.entity.User;

import java.util.Map;

import static net.timxekhach.security.constant.RoleEnum.ROLE_USER;

public class UrlDeclaration {
    public static void startBuildUrl(){
        if(!UrlArchitect.apiUrls.isEmpty()) return;
        UrlArchitect
                .startApi("user").roles(ROLE_USER)
                        .method("login").type(User.class).param("info", Map.class)
                        .method("register").type(User.class).param("user", User.class)
                        .method("forgot-password").param("email", String.class)
                        .method("forgot-password-secret-key").param("secretKeyInfo", Map.class)
                        .method("change-password").param("secretKeyInfo", Map.class)
                        .method("update-user")
                        .method("update-thumbnails")

                .create("caller-staff")
                .create("buss-staff")
        ;

        UrlArchitect
                .startApp("check-in")
                        .child("login")
                        .sibling("register")
                        .sibling("forgot-password")
                        .sibling("logout")
                .create("admin")
                        .child("my-account")
                        .sibling("my-trip").roles(ROLE_USER)
                        .sibling("company-manager")
                        .sibling("caller-employee")
                        .sibling("buss-type")
                        .sibling("buss")
                        .sibling("buss-employee")
                        .sibling("buss-stop")
                        .sibling("ticket")

        ;

    }

}
