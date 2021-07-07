package net.timxekhach.generator.url.model;

import java.util.ArrayList;
import java.util.List;

public class UrlTemplate {
    private String config;
    private String key;
    private List<UrlTemplate> children = new ArrayList<>();
    public UrlTemplate(String key, String config) {
        this.config = config;
        this.key = key;
    }

    public String getConfig() {
        return config;
    }
    public String getKey() {
        return key;
    }
    public List<UrlTemplate> getChildren() {
        return children;
    }
}