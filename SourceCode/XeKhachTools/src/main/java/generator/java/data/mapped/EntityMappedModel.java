package generator.java.data.mapped;

import data.models.Column;
import data.models.MapColumn;
import data.models.PrimaryKey;
import generator.abstracts.models.AbstractEntityModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static generator.GeneratorSetup.API_OPERATION_DATA_MAPPED_ROOT;

@Getter @Setter @SuppressWarnings("rawtypes")
public class EntityMappedModel extends AbstractEntityModel {
    Set<String> imports = new HashSet<>();
    Set<String> staticImports = new HashSet<>();
    List<Column.Core> columns = new ArrayList<>();
    List<MapColumn.Core> mapColumns = new ArrayList<>();
    List<Column.Core> joinIdColumns = new ArrayList<>();
    Set<PrimaryKey> primaryKeys = new HashSet<>();
    List<PkMap> pkMaps = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_MAPPED_ROOT + this.entityClassName + "_MAPPED.java";
    }

    public void filterThenAddImport(String importString) {
        if(!importString.startsWith("java.lang")) {
            imports.add(importString);
        }
    }

}
