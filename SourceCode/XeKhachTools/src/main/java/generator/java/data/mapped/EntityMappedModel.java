package generator.java.data.mapped;

import data.models.Column;
import data.models.MapColumn;
import data.models.PrimaryKey;
import generator.abstracts.models.AbstractEntityModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static generator.GeneratorSetup.API_OPERATION_DATA_MAPPED_ROOT;

@Getter
@Setter
@SuppressWarnings("rawtypes")
public class EntityMappedModel extends AbstractEntityModel {

    @Getter @Setter @AllArgsConstructor
    public static class CountOnInsertDelete {
        String entityCamelName, fieldCapName;
    }
    private static final Map<Class<?>, List<CountOnInsertDelete>> allCountOnInsertDeletes = new HashMap<>();
    void addToAllCountOnInsertDeletes(MapColumn.Core mapCore) {
        Class<?> mapToClass = mapCore.getMapTo().getEntityClass();
        String mapFieldCamelName = mapCore.getFieldName();
        CountOnInsertDelete countOnInsertDelete = new CountOnInsertDelete(this.entityCamelName, mapFieldCamelName);
        List<CountOnInsertDelete> countList = allCountOnInsertDeletes.getOrDefault(mapToClass, new ArrayList<>());
        countList.add(countOnInsertDelete);
        allCountOnInsertDeletes.put(mapToClass, countList);
    }

    public void updateCountOnInsertDelete(){
        this.countOnInsertDeletes = allCountOnInsertDeletes.getOrDefault(this.entity.getClass(), new ArrayList<>());
    }

    Set<String> imports = new HashSet<>(Arrays.asList(
            "javax.persistence.*;",
            "lombok.*;",
            "net.timxekhach.operation.data.mapped.abstracts.XeEntity;",
            "net.timxekhach.operation.data.mapped.abstracts.XePk;",
            "java.util.Map;",
            "net.timxekhach.operation.response.ErrorCode;",
            "org.apache.commons.lang3.math.NumberUtils;",
            "com.fasterxml.jackson.annotation.JsonIgnoreProperties"
    ));
    Set<String> staticImports = new HashSet<>();
    List<Column.Core> columns = new ArrayList<>();
    List<MapColumn.Core> mapColumns = new ArrayList<>();
    List<Column.Core> joinIdColumns = new ArrayList<>();
    Set<PrimaryKey> primaryKeys = new HashSet<>();
    List<PkMap> pkMaps = new ArrayList<>();
    List<CountOnInsertDelete> countOnInsertDeletes = new ArrayList<>();
    List<Column.Core> fieldsAbleAssignByString = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_MAPPED_ROOT + this.entityClassName + "_MAPPED.java";
    }

    public void filterThenAddImport(String importString) {
        if (!importString.startsWith("java.lang")) {
            imports.add(importString);
        }
    }

}
