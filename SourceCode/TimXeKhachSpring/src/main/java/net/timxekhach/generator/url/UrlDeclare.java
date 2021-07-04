package net.timxekhach.generator.url;

import static net.timxekhach.generator.url.AuthEnum.*;
import static net.timxekhach.generator.url.RoleEnum.*;

public class UrlDeclare {
    static UrlBuilder apiUrls = UrlBuilder
            .create("auth")
                .child("login").roles(ROLE_BUSS_STAFF).auths(ADMIN_READ, ADMIN_WRITE)
                .sibling("register")
                .sibling("forgot-password")
                    .child("akjsd")
                    .sibling("dasdasd")
                    .sibling("dakhdasd")
                .uncle("dasd");


    static UrlBuilder appUrls = UrlBuilder
            .create("check-in")
                .child("login")
                .sibling("register")
                .sibling("forgot-pasword");



}
