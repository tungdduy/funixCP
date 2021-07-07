package net.timxekhach.security.handler;

import lombok.Getter;
import net.timxekhach.security.model.UrlNode;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UrlBuilder {
    public static List<UrlNode> apiUrls = new ArrayList<>();
    public static List<UrlNode> appUrls = new ArrayList<>();
    static List<UrlNode> currentUrls;

    public static UrlNode startApi(String url) {
        UrlBuilder.currentUrls = UrlBuilder.apiUrls;
        UrlBuilder.currentUrls.clear();
        return start(url);
    }


    public static UrlNode startApp(String url) {
        UrlBuilder.currentUrls = UrlBuilder.appUrls;
        UrlBuilder.currentUrls.clear();
        return start(url);
    }

    public static UrlNode start(String url) {
        UrlNode urlNode = new UrlNode(url);
        UrlBuilder.currentUrls.add(urlNode);
        return urlNode;
    }

}
