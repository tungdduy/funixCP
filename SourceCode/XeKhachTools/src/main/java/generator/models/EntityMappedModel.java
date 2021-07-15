package generator.models;

import generator.models.abstracts.AbstractTemplateModel;
import generator.models.sub.Column;
import generator.models.sub.Constructor;

import java.util.ArrayList;
import java.util.List;

import static generator.GeneratorSetup.API_OPERATION_DATA_ROOT;

@SuppressWarnings("all")
public class EntityMappedModel extends AbstractTemplateModel {
    List<String> imports = new ArrayList<>();
    List<String> staticImports = new ArrayList<>();
    List<Column> columns = new ArrayList<>();
    List<Constructor> constructors = new ArrayList<>();

    String entityClassName;

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_ROOT + "mapped/" + this.entityClassName + "_MAPPED.java";
    }
}
