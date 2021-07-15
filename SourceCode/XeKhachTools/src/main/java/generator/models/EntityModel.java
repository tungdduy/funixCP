package generator.models;

import generator.models.abstracts.AbstractTemplateModel;
import util.StringUtil;

import static generator.GeneratorSetup.API_OPERATION_DATA_ROOT;

@SuppressWarnings("unused")
public class EntityModel extends AbstractTemplateModel {

    String importSeparator = StringUtil.buildSeparator("IMPORT");
    String importContent;

    String bodySeparator = StringUtil.buildSeparator("BODY");
    String bodyContent;

    String entityClassName;

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_ROOT + "entity/" + this.entityClassName + ".java";
    }
}
