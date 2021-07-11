package net.timxekhach.generator.abstracts;

import lombok.Getter;
import net.timxekhach.security.model.UrlNode;

import javax.annotation.PostConstruct;
import java.io.File;

@Getter
public abstract class AbstractTemplateSource {
    protected File renderFile;
    public File getRenderFile() {
        if(this.renderFile == null && buildRenderFilePath() != null) {
            this.renderFile = new File(buildRenderFilePath());
        }
        return this.renderFile;
    }
    public abstract String buildRenderFilePath();
}
