package net.timxekhach.security.model;

import lombok.Getter;
import net.timxekhach.security.constant.AuthEnum;
import net.timxekhach.security.constant.RoleEnum;
import net.timxekhach.security.handler.UrlBuilder;
import net.timxekhach.utility.XeStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UrlNode {
    private List<AuthEnum> auths = new ArrayList<>();
    private List<RoleEnum> roles = new ArrayList<>();
    private Boolean isPublic;
    private final String url;
    private final List<UrlNode> children = new ArrayList<>();
    private final List<ApiMethod> methods = new ArrayList<>();
    List<UrlNode> ancestors = new ArrayList<>();

    public UrlNode(String url) {
        this.url = url;
    }

    public UrlNode child(String url) {
        UrlNode child = new UrlNode(url);
        child.updateAncestorsForChild(this);
        return child;
    }

    private void updateAncestorsForChild(UrlNode parent) {
        parent.children.add(this);
        this.ancestors = new ArrayList<>(parent.ancestors);
        this.ancestors.add(parent);
    }

    private void updateAncestorsForSibling(UrlNode sibling) {
        sibling.getParent().children.add(this);
        this.ancestors = sibling.ancestors;
    }

    public ApiMethod method(String name) {
        return new ApiMethod(this, name);
    }

    public UrlNode sibling(String url) {
        if(!this.ancestors.isEmpty()) {
            UrlNode sibling = new UrlNode(url);
            sibling.updateAncestorsForSibling(this);
            return sibling;
        }
        return null;
    }

    private UrlNode getParent(){
        return this.ancestors.isEmpty() ? null : this.ancestors.get(this.ancestors.size() - 1);
    }

    UrlNode uncle(String url) {
        return this.getParent().sibling(url);
    }

    public String getCapitalizedName() {
        return XeStringUtils.cssCaseToCapitalizeEachWord(this.url);
    }
    public String getKey(){
        return this.buildUpperCaseUrl();
    }
    public String getComponentName() {
        return XeStringUtils.cssCaseToCapitalizeEachWord(this.url + "-component");
    }
    public String getModuleName() {
        return XeStringUtils.cssCaseToCapitalizeEachWord(this.url + "-module");
    }

    public String buildKeyChain() {
        String ancestorsChain = this.getAncestors().stream()
                .map(UrlNode::buildUpperCaseUrl)
                .collect(Collectors.joining("."));
        return ancestorsChain.isEmpty() ? this.buildUpperCaseUrl() : ancestorsChain + "." + this.buildUpperCaseUrl();
    }

    public String buildUrlChain() {
        String ancestorsChain = this.getAncestors().stream()
                .map(UrlNode::getUrl)
                .collect(Collectors.joining("."));
        return ancestorsChain.isEmpty() ? this.getUrl() : ancestorsChain + "/" + this.getUrl();
    }

    public String buildUpperCaseUrl(){
        return this.url.replaceAll("-", "_").toUpperCase();
    }

    public UrlNode create(String url) {
        return UrlBuilder.start(url);
    }

    public UrlNode auths(AuthEnum... auths) {
        this.auths = Arrays.asList(auths);
        return this;
    }

    public UrlNode roles(RoleEnum... roles) {
        this.roles = Arrays.asList(roles);
        return this;
    }

    UrlNode PUBLIC() {
        this.isPublic = true;
        return this;
    }
}
