package generator.models;

import data.models.Column;
import data.models.MapColumn;
import generator.models.abstracts.AbstractEntityModel;
import generator.models.sub.Constructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static generator.GeneratorSetup.API_OPERATION_DATA_ROOT;

@Getter @Setter
public class EntityMappedModel extends AbstractEntityModel {
    List<String> imports = new ArrayList<>();
    List<String> staticImports = new ArrayList<>();
    List<Column.Core> columns;
    List<MapColumn.Core> mapColumns;
    List<Constructor> constructors = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_ROOT + "mapped/" + this.entityClassName + "_MAPPED.java";
    }
}
