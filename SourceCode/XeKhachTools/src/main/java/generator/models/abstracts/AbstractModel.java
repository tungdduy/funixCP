package generator.models.abstracts;

import generator.models.interfaces.RenderFilePath;
import lombok.Getter;

import java.io.File;

@Getter
public abstract class AbstractModel implements RenderFilePath {
    protected File renderFile;
    public File getRenderFile() {
        if(this.renderFile == null && buildRenderFilePath() != null) {
            this.renderFile = new File(buildRenderFilePath());
        }
        return this.renderFile;
    }
}
