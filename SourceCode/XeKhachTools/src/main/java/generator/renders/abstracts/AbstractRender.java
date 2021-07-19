package generator.renders.abstracts;

import freemarker.template.Template;
import generator.models.abstracts.AbstractModel;
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

public abstract class AbstractRender<E extends AbstractModel> {

    protected abstract boolean isOverrideExistingFile();

    protected String getTemplatePath() {
        return GENERATOR_TEMPLATE_ROOT + getTemplateName();
    }

    protected String getTemplateName() {
        String toLowerDotChain = StringUtil.capitalizeEachWordToLowerDotChain(this.getClass().getSimpleName());
        return StringUtil.removeLastChar(toLowerDotChain, ".render".length()) + ".ftl";
    }

    protected abstract void handleModel(E model);

    public AbstractRender() {
        if (isNewModelOnInit()) {
            E model = newModel();
            this.modelFiles.add(model);
            handleModel(model);
        }
    }

    protected boolean isNewModelOnInit() {
        return true;
    }

    protected E newModel(Object... args) {
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
    private final List<E> modelFiles = new ArrayList<>();

    public void runBeforeRender() {
    }

    public void executeRenders() {
        this.runBeforeRender();
        this.getModelFiles().forEach(root -> {
            Map<String, E> input = new HashMap<>();
            input.put("root", root);
            if (isOverrideExistingFile()) {
                writeToFile(input, template, root.getRenderFile());
            } else {
                writeToNoneExistFileOnly(input, template, root.getRenderFile());
            }
        });
    }

    protected boolean isManualRender() {
        return false;
    }

    private static void renderAll() {
        batchNewThenExecuteRenders();
        AbstractUrlRender.renderWithParent();
        AbstractEntityRender.renderWithParent();
    }

    public static void renderByGroup(RenderGroup group) {
        if (group == RenderGroup.ALL) {
           renderAll();
        }
        if (group == RenderGroup.ENTITY) {
            AbstractEntityRender.standaloneRender();
        }
        if (group == RenderGroup.URL) {
           AbstractUrlRender.standaloneRender();
        }

        if (group == RenderGroup.OTHER) {
           batchNewThenExecuteRenders();
        }
    }


    @SuppressWarnings("rawtypes")
    private static void batchNewThenExecuteRenders() {
        List<? extends AbstractRender> renders = batchNewAllChildrenRenders(AbstractRender.class);
        renders.forEach(render -> {
            if (!render.isManualRender()) {
                render.executeRenders();
            }
        });
    }

    @SuppressWarnings("rawtypes")
    protected static List<? extends AbstractRender> batchNewAllChildrenRenders(Class<? extends AbstractRender> clazz) {
        String packageName = StringUtil.removeLastChar(clazz.getPackage().getName(), ".abstract".length());
        return ReflectionUtil.newInstancesOfAllChildren(clazz, packageName);
    }
}
