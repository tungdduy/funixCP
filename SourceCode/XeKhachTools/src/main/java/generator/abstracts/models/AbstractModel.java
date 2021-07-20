package generator.abstracts.models;

import generator.abstracts.interfaces.SeparatorContent;
import lombok.Getter;

import java.io.File;
import java.io.Serializable;

@Getter
public abstract class AbstractModel implements SeparatorContent, Serializable {
    protected File renderFile;

    public File getRenderFile() {
        if (this.renderFile == null && buildRenderFilePath() != null) {
            this.renderFile = new File(buildRenderFilePath());
        }
        return this.renderFile;
    }

    public AbstractModel() {
        init();
    }

    protected void init() {
        updateSeparatorContent();
    }

}
