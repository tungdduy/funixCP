package architect;

import architect.urls.UrlArchitect;

import static util.constants.RoleEnum.*;


public class UrlDeclaration {
    public static void startBuildUrl() {
        if (!UrlArchitect.apiUrls.isEmpty()) return;
        UrlArchitect
                .startApi("user")
                    .method("login")
                    .method("register")
                    .method("forgot-password")
                    .method("forgot-password-secret-key")
                    .method("change-password")
                    .method("update-password")
                .create("trip")
                    .method("searchLocation")
                    .method("findBussSchedules")
                    .method("findScheduledLocations")
                    .method("getTripUsers")
                    .method("getTripByCompanyId").roles(ROLE_BUSS_STAFF, ROLE_CALLER_STAFF)
                    .method("getBussSchedulesByCompanyId").roles(ROLE_BUSS_STAFF, ROLE_CALLER_STAFF)
                .create("common-update").roles(ROLE_USER)

        ;

        UrlArchitect
                .startApp("check-in")
                    .child("login")
                    .sibling("register")
                    .sibling("forgot-password")
                    .sibling("logout")
                .create("admin")
                    .child("my-account").roles(ROLE_USER)
                    .sibling("my-company").roles(ROLE_BUSS_ADMIN)
                    .sibling("all-user").roles(ROLE_SYS_ADMIN)
                    .sibling("my-trip")
                    .sibling("find-trip")
                    .sibling("company-manager").roles(ROLE_SYS_ADMIN)
                    .sibling("buss-type").roles(ROLE_SYS_ADMIN)
                    .sibling("buss").roles(ROLE_BUSS_STAFF, ROLE_CALLER_STAFF)
                    .sibling("employee").roles(ROLE_BUSS_ADMIN)
                    .sibling("path").roles(ROLE_BUSS_STAFF, ROLE_CALLER_STAFF)
                    .sibling("ticket").roles(ROLE_BUSS_STAFF, ROLE_CALLER_STAFF)

        ;


    }

}
