package generator.java.data.mapped;

import data.models.Column;
import data.models.CountMethod;
import data.models.MapColumn;
import data.models.PrimaryKey;
import generator.abstracts.models.AbstractEntityModel;
import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static generator.GeneratorSetup.API_OPERATION_DATA_MAPPED_ROOT;

@Getter
@Setter
@SuppressWarnings("rawtypes")
public class EntityMappedModel extends AbstractEntityModel {
    protected List<String> pkIdChains = new ArrayList<>();

    @Override
    public void prepareSeparator() {
        separator("import").unique(
                StringUtil.toImportFormat(
                        "javax.persistence.*",
                        "lombok.*",
                        "net.timxekhach.operation.data.mapped.abstracts.XeEntity",
                        "net.timxekhach.operation.data.mapped.abstracts.XePk",
                        "java.util.Map",
                        "net.timxekhach.operation.response.ErrorCode",
                        "org.apache.commons.lang3.math.NumberUtils",
                        "com.fasterxml.jackson.annotation.JsonIgnoreProperties"
                )).newOnly();

    }

    protected List<Column.Core> columns = new ArrayList<>();
    protected List<MapColumn.Core> mapColumns = new ArrayList<>();
    protected List<Column.Core> joinIdColumns = new ArrayList<>();
    protected Set<PrimaryKey> primaryKeys = new HashSet<>();
    protected List<PkMap> pkMaps = new ArrayList<>();
    protected List<Column.Core> fieldsAbleAssignByString = new ArrayList<>();
    protected List<CountMethod> countMethods = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_MAPPED_ROOT + this.entityCapName + "_MAPPED.java";
    }

    public void filterThenAddImport(String... importStrings) {
        if (importStrings == null) return;
        for (String importString : importStrings) {
            if (importString.startsWith("util.constants.")) {
                importString = importString.replace("util.constants.", "net.timxekhach.operation.data.enumeration.");
            }
            if (!importString.startsWith("java.lang")) {
                String importContent = String.format("import %s;", importString);
                this.separator("import").unique(importContent);
            }
        }
    }

}
