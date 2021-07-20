package architect.urls;

import generator.builders.UrlNodeBuilder;
import generator.abstracts.interfaces.AuthConfig;
import lombok.Getter;
import net.timxekhach.security.constant.RoleEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class UrlNode implements AuthConfig {
    private final UrlTypeEnum urlType;
    private List<RoleEnum> roles = new ArrayList<>();
    private Boolean isPublic;
    private final String url;
    private final List<UrlNode> children = new ArrayList<>();
    private final List<ApiMethod> methods = new ArrayList<>();
    List<UrlNode> ancestors = new ArrayList<>();

    public UrlNode(String url) {
        this.urlType = UrlArchitect.buildingUrlType;
        this.url = url;
    }

    public UrlNode child(String url) {
        UrlNode child = new UrlNode(url);
        child.addParent(this);
        return child;
    }

    private void addParent(UrlNode parent) {
        parent.children.add(this);
        this.ancestors = new ArrayList<>(parent.ancestors);
        this.ancestors.add(parent);
    }

    private void addSibling(UrlNode sibling) {
        this.getParent().children.add(sibling);
        sibling.ancestors = this.ancestors;
    }

    public ApiMethod method(String name) {
        return new ApiMethod(this, name);
    }

    public UrlNode sibling(String url) {
        if(!this.ancestors.isEmpty()) {
            UrlNode sibling = new UrlNode(url);
            this.addSibling(sibling);
            return sibling;
        }
        return null;
    }

    public UrlNode getParent(){
        return this.ancestors.isEmpty() ? null : this.ancestors.get(this.ancestors.size() - 1);
    }

    UrlNode uncle(String url) {
        return this.getParent().sibling(url);
    }


    public UrlNode create(String url) {
        return UrlArchitect.start(url);
    }

    public UrlNode roles(RoleEnum... roles) {
        this.roles = Arrays.asList(roles);
        return this;
    }
    UrlNode PUBLIC() {
        this.isPublic = true;
        return this;
    }

    private UrlNodeBuilder builder;
    public UrlNodeBuilder getBuilder() {
        if(this.builder == null) {
            this.builder = new UrlNodeBuilder(this);
        }
        return this.builder;
    }
}
