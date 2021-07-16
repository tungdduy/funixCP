package generator.models;

import data.entities.abstracts.AbstractEntity;
import generator.models.abstracts.AbstractEntityModel;
import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import static generator.GeneratorSetup.API_OPERATION_DATA_ROOT;

@SuppressWarnings("all") @Getter @Setter
public class EntityModel extends AbstractEntityModel {

    String importSeparator = StringUtil.buildSeparator("IMPORT");
    String importContent;

    String bodySeparator = StringUtil.buildSeparator("BODY");
    String bodyContent;

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_ROOT + "entity/" + this.entityClassName + ".java";
    }
}
