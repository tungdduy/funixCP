package net.timxekhach.security.handler;

import lombok.Getter;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.security.model.UrlTypeEnum.UrlTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class UrlArchitect {
    public static List<UrlNode> apiUrls = new ArrayList<>();
    public static List<UrlNode> appUrls = new ArrayList<>();
    private static UrlTypeEnum currentType;
    static List<UrlNode> currentUrls;

    public static UrlNode startApi(String url) {
        currentUrls = UrlArchitect.apiUrls;
        currentUrls.clear();
        currentType = UrlTypeEnum.API;
        return start(url);
    }

    public static UrlNode startApp(String url) {
        currentUrls = UrlArchitect.appUrls;
        currentUrls.clear();
        currentType = UrlTypeEnum.APP;
        return start(url);
    }

    public static UrlNode start(String url) {
        UrlNode urlNode = new UrlNode(url);
        urlNode.setUrlType(currentType);
        UrlArchitect.currentUrls.add(urlNode);
        return urlNode;
    }

    public static List<UrlNode> flatAll(List<UrlNode> result, List<UrlNode> hierarchies) {
        hierarchies.forEach(url -> {
            result.add(url);
            flatAll(result, url.getChildren());
        });
        return result;
    }

    public static void traverseApiUrls(Consumer<UrlNode> consumer) {
        traverse(apiUrls, consumer);
    }

    public static void traverseAppUrls(Consumer<UrlNode> consumer) {
        traverse(appUrls, consumer);
    }

    private static void traverse(List<UrlNode> apiUrls, Consumer<UrlNode> consumer) {
        apiUrls.forEach(url -> {
            consumer.accept(url);
            traverse(url.getChildren(), consumer);
        });
    }
}
