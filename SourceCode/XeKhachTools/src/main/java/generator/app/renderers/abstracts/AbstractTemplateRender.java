package generator.app.renderers.abstracts;

import freemarker.template.Template;
import generator.GenerationCentral;
import generator.app.models.abstracts.AbstractTemplateModel;
import generator.app.renderers.ApiMessagesTsFtl;
import lombok.Getter;
import net.timxekhach.utility.XeReflectionUtils;
import net.timxekhach.utility.XeStringUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static generator.GenerationCentral.TEMPLATE_ROOT;

public abstract class AbstractTemplateRender<E extends AbstractTemplateModel> {

    protected abstract boolean isOverrideExistingFile();
    protected String getTemplatePath() {
        return TEMPLATE_ROOT + getTemplateName();
    }
    protected String getTemplateName(){
        return XeStringUtils.capitalizeEachWordToLowerDotChain(this.getClass().getSimpleName()) ;
    }
    protected abstract void handleSource(E source);

    public AbstractTemplateRender() {

        if(needHandleSourceOnInit()) {
            E source = newSource();
            this.renderFiles.add(source);
            handleSource(source);
        }
    }

    protected boolean needHandleSourceOnInit() {
        return true;
    }

    protected E newSource() {
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

    private final Template template = GenerationCentral.prepareTemplate(new File(getTemplatePath()));

    @Getter
    private final List<E> renderFiles = new ArrayList<>();

    public void prepareRenderFiles() {}

    public void executeRenderFiles() {
        this.prepareRenderFiles();
        this.getRenderFiles().forEach(root -> {
            Map<String, E> input = new HashMap<>();
            input.put("root", root);
            if(isOverrideExistingFile()) {
                GenerationCentral.writeToFile(input, template, root.getRenderFile());
            } else {
                GenerationCentral.writeToNoneExistFileOnly(input, template, root.getRenderFile());
            }
        });
    }

    protected boolean isBuildFileManually(){
        return false;
    }

    @SuppressWarnings("rawtypes")
    public static void buildAll() {
        String packageName = ApiMessagesTsFtl.class.getPackage().getName();
        List<? extends AbstractTemplateRender> builders
                = XeReflectionUtils.newInstancesOfAllChildren(AbstractTemplateRender.class, packageName);
        builders.forEach(builder -> {
            if (!builder.isBuildFileManually()) {
               builder.executeRenderFiles();
            }
        });
    }

}
