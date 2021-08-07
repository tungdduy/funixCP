package generator.abstracts.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractRestModel extends AbstractUrlModel {

    @Override
    public void prepareSeparator() {
        this.separator("body");
        this.separator("import");
    }

    protected String
            packagePath,
            capName;
}
