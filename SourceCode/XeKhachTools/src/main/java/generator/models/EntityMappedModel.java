package generator.models;

import data.models.Column;
import data.models.MapColumn;
import data.models.PrimaryKey;
import generator.models.abstracts.AbstractEntityModel;
import generator.models.sub.Param;
import generator.models.sub.PkMap;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static generator.GeneratorSetup.API_OPERATION_DATA_ROOT;

@Getter @Setter @SuppressWarnings("rawtypes")
public class EntityMappedModel extends AbstractEntityModel {
    List<String> imports = new ArrayList<>();
    List<String> staticImports = new ArrayList<>();
    List<Column.Core> columns = new ArrayList<>();
    List<MapColumn.Core> mapColumns = new ArrayList<>();
    List<PrimaryKey> primaryKeys = new ArrayList<>();
    List<Param> constructorParams = new ArrayList<>();
    List<PkMap> pkMaps = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_ROOT + "mapped/" + this.entityClassName + "_MAPPED.java";
    }
}
