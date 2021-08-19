package generator.java.data.entity;

import generator.abstracts.models.AbstractEntityModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static generator.GeneratorSetup.API_OPERATION_DATA_ENTITY_ROOT;

@SuppressWarnings("all") @Getter @Setter
public class EntityModel extends AbstractEntityModel {

    @Override
    public void prepareSeparator() {
        separator("body");
        separator("import").unique(
                "import lombok.Getter;",
                "import lombok.Setter;",
                "import javax.persistence.Entity;",
                String.format("import net.timxekhach.operation.data.mapped.%s_MAPPED;", this.entityCapName));
    }
    Boolean hasProfileImage = false;

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_ENTITY_ROOT + this.entityCapName + ".java";
    }

}
