package generator.builders;

import architect.urls.UrlNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UrlNodeBuilder {
    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final UrlNode urlNode;

    public UrlNodeBuilder(UrlNode urlNode) {
        this.urlNode = urlNode;
    }

    public String buildCapitalizeName() {
        return StringUtil.toCapitalizeEachWord(this.urlNode.getUrl());
    }

    public String buildCamelName() {
        return StringUtil.toCamel(this.urlNode.getUrl());
    }

    public String buildLowercaseCharsOnly() {
        return StringUtil.toLowercaseCharsOnly(this.urlNode.getUrl());
    }

    public String buildControllerPackagePath() {
        return buildPackagePath("api");
    }

//    public String buildControllerFilePath() {
//        return buildControllerPackagePath().replaceAll("\\.", "/").concat(".java");
//    }

    public String buildFullControllerClassName() {
        return StringUtil.joinByDot(this.buildControllerPackagePath(), this.buildCapitalizeName() + "Api");
    }

    public String buildFullServiceClassName() {
        return StringUtil.joinByDot(this.buildServicePackagePath(), this.buildCapitalizeName() + "Service");
    }

    public String buildServicePackagePath() {
       return buildPackagePath("service");
    }

    private String buildPackagePath(String prefix) {
        List<String> ancestorChain = new ArrayList<>();
        this.urlNode.getAncestors().forEach(ancestor -> ancestorChain.add(ancestor.getBuilder().buildLowercaseCharsOnly()));
        String packagePath = "net.timxekhach.operation.rest." + prefix;
        if(!ancestorChain.isEmpty()) {
            packagePath += "." + StringUtil.joinByDot(ancestorChain);
        }
        return packagePath;
    }

    public Class<?> findControllerClass() {
        try {
            return Class.forName(buildFullControllerClassName());
        } catch (ClassNotFoundException e) {
            logger.debug(String.format("Not found class for %s", this.buildFullControllerClassName()));
        }
        return null;
    }

    public Class<?> findServiceClass() {
        try {
            return Class.forName(buildFullServiceClassName());
        } catch (ClassNotFoundException e) {
           logger.debug(String.format("service %s not found, will create new!", buildFullServiceClassName()));
        }
        return null;
    }

    public String buildKey(){
        return StringUtil.toUPPER_UNDERLINE(this.urlNode.getUrl());
    }
    public String buildComponentName() {
        return StringUtil.toCapitalizeEachWord(this.urlNode.getUrl() + "-component");
    }
    public String buildModuleName() {
        return StringUtil.toCapitalizeEachWord(this.urlNode.getUrl() + "-module");
    }

    public String buildKeyChain() {
        String ancestorsChain = this.urlNode.getAncestors().stream()
                .map(UrlNode::getUrl)
                .map(StringUtil::toUPPER_UNDERLINE)
                .collect(Collectors.joining("."));
        return ancestorsChain.isEmpty()
                ? this.buildKey()
                : ancestorsChain + "." + this.buildKey();
    }

    public String buildUrlChain() {
        String ancestorsChain = this.urlNode.getAncestors().stream()
                .map(UrlNode::getUrl)
                .collect(Collectors.joining("."));
        return ancestorsChain.isEmpty()
                ? this.urlNode.getUrl()
                : ancestorsChain + "/" + this.urlNode.getUrl();
    }

    public boolean isModule() {
        return this.urlNode.getChildren().size() > 0;
    }

    public int getLevel(){
        return urlNode.getAncestors().size();
    }
}
