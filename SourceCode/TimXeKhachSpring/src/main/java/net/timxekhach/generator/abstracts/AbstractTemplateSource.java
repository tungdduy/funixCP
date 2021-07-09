package net.timxekhach.generator.abstracts;

import lombok.Getter;
import net.timxekhach.security.model.UrlNode;

import java.io.File;

@Getter
public abstract class AbstractTemplateSource {
    protected File renderFile;
    protected AbstractTemplateSource(){
        renderFile = new File(buildRenderFilePath());
        this.init();
    }
    protected AbstractTemplateSource(UrlNode urlNode){
        this();
    }
    protected void init() {};
    protected abstract String buildRenderFilePath();
}
