package generator.abstracts.models;

import generator.abstracts.interfaces.SeparatorContent;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.Serializable;

@Getter @Setter
public abstract class AbstractModel extends SeparatorContent implements Serializable {
    protected File renderFile;

    public File getRenderFile() {
        return new File(buildRenderFilePath());
    }

    public AbstractModel() {
        init();
    }

    protected void init() {
    }

}
