package architect;

import architect.urls.UrlArchitect;
import data.entities.User;

import java.util.List;
import java.util.Map;

import static util.constants.RoleEnum.*;


public class UrlDeclaration {
    public static void startBuildUrl(){
        if(!UrlArchitect.apiUrls.isEmpty()) return;
        UrlArchitect
                .startApi("user")
                        .method("login").type(User.class).param("info", Map.class)
                        .method("register").type(User.class).param("user", Map.class)
                        .method("forgot-password").param("email", String.class)
                        .method("forgot-password-secret-key").param("secretKeyInfo", Map.class)
                        .method("change-password").param("secretKeyInfo", Map.class)
                        .method("update-password").param("data", Map.class)
                .create("trip")
                        .method("available-seats").type(List.class).param("data", Map.class)
                        .method("available-trips").type(List.class)
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
                        .child("my-account").roles(ROLE_SYS_ADMIN)
                        .sibling("all-user").roles(ROLE_SYS_ADMIN)
                        .sibling("my-trip").roles(ROLE_USER)
                        .sibling("company-manager").roles(ROLE_SYS_ADMIN)
                        .sibling("caller-employee").roles(ROLE_BUSS_ADMIN)
                        .sibling("buss-type").roles(ROLE_SYS_ADMIN)
                        .sibling("buss").roles(ROLE_BUSS_STAFF)
                        .sibling("buss-employee").roles(ROLE_BUSS_ADMIN)
                        .sibling("buss-stop").roles(ROLE_BUSS_ADMIN)
                        .sibling("ticket").roles(ROLE_BUSS_STAFF, ROLE_CALLER_STAFF)

        ;


    }

}
