package net.timxekhach.generator.abstracts;

import freemarker.template.Template;
import lombok.Getter;
import net.timxekhach.generator.GeneratorCenter;
import net.timxekhach.generator.renderers.ApiMessagesTsFtl;
import net.timxekhach.utility.XeReflectionUtils;
import net.timxekhach.utility.XeStringUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.timxekhach.generator.GeneratorCenter.TEMPLATE_ROOT;

public abstract class AbstractTemplateBuilder<E extends AbstractTemplateSource> {

    protected abstract boolean isOverrideExistingFile();
    protected String getTemplatePath() {
        return TEMPLATE_ROOT + getTemplateName();
    }
    protected String getTemplateName(){
        return XeStringUtils.capitalizeEachWordToLowerDotChain(this.getClass().getSimpleName()) ;
    }
    protected abstract void handleSource(E source);

    public AbstractTemplateBuilder() {

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

    private final Template template = GeneratorCenter.prepareTemplate(new File(getTemplatePath()));

    @Getter
    private final List<E> renderFiles = new ArrayList<>();

    public void prepareRenderFiles() {}

    public void executeRenderFiles() {
        this.prepareRenderFiles();
        this.getRenderFiles().forEach(root -> {
            Map<String, E> input = new HashMap<>();
            input.put("root", root);
            if(isOverrideExistingFile()) {
                GeneratorCenter.writeToFile(input, template, root.getRenderFile());
            } else {
                GeneratorCenter.writeToNoneExistFileOnly(input, template, root.getRenderFile());
            }
        });
    }

    protected boolean isBuildFileManually(){
        return false;
    }

    @SuppressWarnings("rawtypes")
    public static void buildAll() {
        String packageName = ApiMessagesTsFtl.class.getPackage().getName();
        List<? extends AbstractTemplateBuilder> builders
                = XeReflectionUtils.newInstancesOfAllChildren(AbstractTemplateBuilder.class, packageName);
        builders.forEach(builder -> {
            if (!builder.isBuildFileManually()) {
               builder.executeRenderFiles();
            }
        });
    }

}
