package net.timxekhach.generator.abstracts;

import freemarker.template.Template;
import net.timxekhach.generator.GeneratorCenter;
import net.timxekhach.utility.XeStringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.timxekhach.generator.GeneratorCenter.TEMPLATE_ROOT;

public abstract class AbstractTemplateBuilder<E extends AbstractTemplateSource> {
    protected String getTemplatePath() {
        return TEMPLATE_ROOT + getTemplateName();
    }
    protected String getTemplateName(){
        return XeStringUtils.capitalizeEachWordToLowerDotChain(this.getClass().getSimpleName()) ;
    }

    protected boolean isOverrideExistingFile() {return false;};

    private final Template template = GeneratorCenter.prepareTemplate(new File(getTemplatePath()));

    protected abstract List<E> prepareRenderFiles();

    public void executeRenderFiles() {
        prepareRenderFiles().forEach(root -> {
            Map<String, Object> input = new HashMap<>();
            input.put("root", root);
            if(isOverrideExistingFile()) {
                GeneratorCenter.writeToFile(input, template, root.getRenderFile());
            } else {
                GeneratorCenter.writeToNoneExistFileOnly(input, template, root.getRenderFile());
            }
        });
    }

}
