package generator.renders.abstracts;

import freemarker.template.Template;
import generator.models.abstracts.AbstractTemplateModel;
import generator.renders.ApiMessagesTsRender;
import lombok.Getter;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import util.ReflectionUtil;
import util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static generator.GeneratorSetup.*;

public abstract class AbstractTemplateRender<E extends AbstractTemplateModel> {

    public static final String APP_TEMPLATE_ROOT = GENERATOR_ROOT + "app/templates/";

    protected abstract boolean isOverrideExistingFile();

    protected String getTemplatePath() {
        return APP_TEMPLATE_ROOT + getTemplateName();
    }

    protected String getTemplateName() {
        String toLowerDotChain = StringUtil.capitalizeEachWordToLowerDotChain(this.getClass().getSimpleName());
        return StringUtil.removeLastChar(toLowerDotChain, ".render".length()) + ".ftl";
    }

    protected abstract void handleModel(E model);

    public AbstractTemplateRender() {

        if (isNewModelOnInit()) {
            E model = newModel();
            this.renderFiles.add(model);
            handleModel(model);
        }
    }

    protected boolean isNewModelOnInit() {
        return true;
    }

    protected E newModel() {
        try {
            return getParameterizeClass()
                    .getConstructor()
                    .newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected Class<E> getParameterizeClass() {
        return (Class<E>) ((ParameterizedTypeImpl) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    private final Template template = prepareTemplate(new File(getTemplatePath()));

    @Getter
    private final List<E> renderFiles = new ArrayList<>();

    public void prepareRenderFiles() {
    }

    public void executeRenderFiles() {
        this.prepareRenderFiles();
        this.getRenderFiles().forEach(root -> {
            Map<String, E> input = new HashMap<>();
            input.put("root", root);
            if (isOverrideExistingFile()) {
                writeToFile(input, template, root.getRenderFile());
            } else {
                writeToNoneExistFileOnly(input, template, root.getRenderFile());
            }
        });
    }

    protected boolean isBuildFileManually() {
        return false;
    }

    @SuppressWarnings("rawtypes")
    public static void buildAll() {
        String packageName = ApiMessagesTsRender.class.getPackage().getName();
        List<? extends AbstractTemplateRender> builders
                = ReflectionUtil.newInstancesOfAllChildren(AbstractTemplateRender.class, packageName);
        builders.forEach(builder -> {
            if (!builder.isBuildFileManually()) {
                builder.executeRenderFiles();
            }
        });
    }

}
