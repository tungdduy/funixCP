package generator.models.abstracts;

import lombok.Getter;

import java.io.File;

@Getter
public abstract class AbstractTemplateModel {
    protected File renderFile;
    public File getRenderFile() {
        if(this.renderFile == null && buildRenderFilePath() != null) {
            this.renderFile = new File(buildRenderFilePath());
        }
        return this.renderFile;
    }
    public abstract String buildRenderFilePath();
}
