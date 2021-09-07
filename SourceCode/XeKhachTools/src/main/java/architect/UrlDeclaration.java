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
                    .method("change-password").roles(ROLE_USER)
                    .method("update-password").roles(ROLE_USER)
                .create("trip")
                    .method("search-location")
                    .method("find-buss-schedules")
                    .method("find-scheduled-locations")
                    .method("get-trip-users")
                    .method("get-trip-by-company-id").roles(ROLE_BUSS_STAFF, ROLE_CALLER_STAFF)
                    .method("get-buss-schedules-by-company-id").roles(ROLE_BUSS_STAFF, ROLE_CALLER_STAFF)
                .create("common-update")
                    .method("Trip").roles(ROLE_CALLER_STAFF, ROLE_BUSS_STAFF)
                    .method("TripUser")
                    .method("User").roles(ROLE_USER)
                    .method("Buss").roles(ROLE_USER)
                    .method("BussSchedule").roles(ROLE_USER)
                    .method("BussSchedulePoint").roles(ROLE_USER)
                    .method("BussType").roles(ROLE_SYS_ADMIN)
                    .method("Company").roles(ROLE_BUSS_ADMIN)
                    .method("Employee").roles(ROLE_USER)
                    .method("Location").roles(ROLE_SYS_ADMIN)
                    .method("Path").roles(ROLE_BUSS_ADMIN)
                    .method("PathPoint").roles(ROLE_BUSS_ADMIN)
                    .method("SeatGroup").roles(ROLE_SYS_ADMIN)


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
