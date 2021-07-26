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

    Set<String> importRequireLines = new HashSet<>(Arrays.asList(
            "import lombok.Getter;",
            "import lombok.Setter;",
            "import javax.persistence.Entity;",
            String.format("import net.timxekhach.operation.data.mapped.%s_MAPPED;", this.entityClassName)
    ));

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_ENTITY_ROOT + this.entityClassName + ".java";
    }

}
