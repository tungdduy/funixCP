package net.timxekhach.generator.renderers;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractApiUrlTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.generator.url.UrlNodeBuilder;
import net.timxekhach.security.model.ApiMethod;
import net.timxekhach.security.model.UrlNode;
import net.timxekhach.utility.XeFileUtil;
import net.timxekhach.utility.XeResponseUtils;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.timxekhach.generator.GeneratorCenter.API_OPERATION_REST_API_ROOT;

public class RestApiFtl<E extends AbstractTemplateSource> extends AbstractApiUrlTemplateBuilder<E> {

    @Override
    @SuppressWarnings("unchecked")
    protected E visitUrlNode(UrlNode urlNode) {
        return (E) new AbstractTemplateSource(urlNode) {

            @Getter
            private final String IMPORT_SEPARATOR = String.format("\t%s\n\t%s\n\t%s",
                    "//========================================//",
                    "// _______ START_IMPORT_METHOD __________ //",
                    "//========================================//");
            @Getter
            private String
                    importContent,
                    aboveImportContent,
                    belowImportContent;

            @Override
            @SuppressWarnings("rawtypes")
            protected void init() {
                String[] currentContent = XeFileUtil.readAsString(this.buildRenderFilePath()).split(IMPORT_SEPARATOR);
                if (currentContent.length > 1) {
                    aboveImportContent = currentContent[0];
                    belowImportContent = currentContent[1];
                    Class controllerClass =  urlNode.getBuilder().findControllerClass();
                    List<ApiMethod> importMethods = urlNode.getMethods().stream()
                            .filter(method -> excludeExistedMethods(controllerClass, method))
                            .collect(Collectors.toList());
                    importContent = buildImportContent(importMethods);
                } else {
                    aboveImportContent = buildFullAboveImportContent();
                    belowImportContent = buildFullBelowImportContent();
                    buildImportContent(urlNode.getMethods());
                }
            }

            private boolean excludeExistedMethods(Class controllerClass, ApiMethod method) {
                try {
                    controllerClass.getMethod(method.getName(), method.getBuilder().getAllParamTypes());
                    return false;
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return true;
            }

            private String buildImportContent(List<ApiMethod> methods) {
                StringBuilder sb = new StringBuilder();
                methods.forEach(method -> sb.append(method.getBuilder().buildFullMethodString()));
                return sb.toString();
            }

            private String buildFullBelowImportContent() {
                return "\n\n}";
            }

            private String buildFullAboveImportContent() {
                List<String> content = new ArrayList<>();
                UrlNodeBuilder builder = urlNode.getBuilder();
                String capitalizeName = builder.buildCapitalizeName();
                String camelName = builder.buildCamelName();
                content.add(String.format("package %s;", builder.buildControllerPackagePath()));
                content.add("import org.springframework.web.bind.annotation.*;");
                content.add("import javax.validation.*;");
                content.add(String.format("import %s ;", ResponseEntity.class.getName()));
                content.add(String.format("import %s ;", builder.buildFullServiceClassName()));
                content.add(String.format("import %s ;", XeResponseUtils.class.getName()));
                content.add(String.format("import %s ;", Map.class.getName()));
                content.add("\n");
                content.add("@RestController");
                content.add(String.format("@RequestMapping(path = {\"/%s\"})", urlNode.getUrl()));
                content.add(String.format("public class %sApi {", capitalizeName));
                content.add("\n");
                content.add(String.format("\tprivate final %sService %s;", capitalizeName, camelName));
                content.add(String.format("\tpublic %sApi(%sService %sService) {", capitalizeName, capitalizeName, camelName));
                content.add(String.format("\t\tthis.%sService = %sService;", camelName, camelName));
                content.add("\t}");

                return String.join("\n", content);
            }

            @Override
            protected String buildRenderFilePath() {
                return API_OPERATION_REST_API_ROOT + urlNode.getBuilder().buildCapitalizeName() + ".java";
            }
        };
    }
}
