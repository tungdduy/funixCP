package generator.abstracts.render;

import freemarker.template.Template;
import generator.abstracts.interfaces.RenderGroup;
import generator.abstracts.models.AbstractModel;
import lombok.Getter;
import net.timxekhach.utility.XeFileUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import util.StringUtil;

import java.io.File;
import java.util.*;
import java.util.function.Function;

import static generator.GeneratorSetup.*;
import static util.ObjectUtil.newInstanceFromClass;

public abstract class AbstractRender<E extends AbstractModel> {

    protected abstract boolean isOverrideExistingFile();

    protected String getTemplatePath() {
        return TOOLS_ROOT + this.getClass().getPackage().getName().replace(".", "/") + "/" + getTemplateName();
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
    protected static List<? extends AbstractRender> batchNewAllChildrenRenders(Class<? extends AbstractRender> parentClass) {
        return XeFileUtils.fetchAllPossibleFiles(TOOL_GENERATOR_ROOT,
                getChildrenRender(parentClass),
                newInstanceFromClass());
    }

    @SuppressWarnings("rawtypes")
    public static Function getChildrenRender(Class<? extends AbstractRender> parentClass) {
        return object -> {
            File file = (File) object;
            String className = file.getPath().substring(TOOLS_ROOT.length(), file.getPath().length() - ".java".length()).replace("\\", ".");
            if (!className.startsWith("generator.builder")
                    && !className.startsWith("generator.abstract")
                    && className.endsWith("Render")) {
                try {
                    Class<?> clazz = Class.forName(className);
                    if (parentClass.isAssignableFrom(clazz))
                        return clazz;
                } catch (ClassNotFoundException e) {
                    System.out.println("cannot make class for " + className);
                }
            }
            return null;
        };
    }

}
