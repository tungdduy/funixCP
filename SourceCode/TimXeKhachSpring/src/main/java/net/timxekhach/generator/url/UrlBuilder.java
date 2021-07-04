package net.timxekhach.generator.url;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrlBuilder {
    private String url;
    List<AuthEnum> auths;
    List<RoleEnum> roles;
    boolean isPublic = true;
    UrlBuilder parent;
    List<UrlBuilder> children = new ArrayList<>();

    UrlBuilder(String url) {
        this.url = url;
    }

    static UrlBuilder create(String url) {
        return new UrlBuilder(url);
    }

    UrlBuilder auths(AuthEnum... auths) {
        this.auths = Arrays.asList(auths);
        return this;
    }

    UrlBuilder roles(RoleEnum... roles) {
        this.roles = Arrays.asList(roles);
        return this;
    }

    UrlBuilder notPublic() {
        this.isPublic = false;
        return this;
    }

    UrlBuilder child(String url) {
        UrlBuilder child = new UrlBuilder(url);
        this.children.add(child);
        child.parent = this;
        return child;
    }

    UrlBuilder sibling(String url) {
        if(this.parent != null) {
            UrlBuilder sibling = new UrlBuilder(url);
            sibling.parent = this.parent;
            this.parent.children.add(sibling);
        }
        return null;
    }

    UrlBuilder back() {
        return this.parent;
    }

    UrlBuilder uncle(String url) {
        return this.parent.sibling(url);
    }
}
