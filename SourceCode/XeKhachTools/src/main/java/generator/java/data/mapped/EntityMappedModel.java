package generator.java.data.mapped;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.CountMethod;
import data.models.MapColumn;
import data.models.PrimaryKey;
import generator.abstracts.models.AbstractEntityModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import util.ObjectUtil;
import util.ReflectionUtil;
import util.StringUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static generator.GeneratorSetup.API_OPERATION_DATA_MAPPED_ROOT;
import static util.StringUtil.toCamel;
import static util.StringUtil.toCapitalizeEachWord;

@Getter
@Setter
@SuppressWarnings("rawtypes")
public class EntityMappedModel extends AbstractEntityModel {
    protected List<String> pkIdChains = new ArrayList<>();
    protected List<Column.Core> columns = new ArrayList<>();
    protected List<MapColumn.Core> mapColumns = new ArrayList<>();
    protected List<Column.Core> joinIdColumns = new ArrayList<>();
    protected Set<PrimaryKey> primaryKeys = new HashSet<>();
    protected List<PkMap> pkMaps = new ArrayList<>();
    protected List<Column.Core> fieldsAbleAssignByString = new ArrayList<>();
    protected List<CountMethod> countMethods = new ArrayList<>();

    protected static final String NEW_ARRAY_LIST = " = new ArrayList<>()";

    @Override
    public void prepareSeparator() {
        separator("import").unique(
                StringUtil.toImportFormat(
                        "com.fasterxml.jackson.annotation.JsonIgnore",
                        "javax.persistence.*",
                        "lombok.*",
                        "net.timxekhach.operation.data.mapped.abstracts.XeEntity",
                        "net.timxekhach.operation.data.mapped.abstracts.XePk",
                        "java.util.Map",
                        "net.timxekhach.operation.response.ErrorCode",
                        "org.apache.commons.lang3.math.NumberUtils",
                        "com.fasterxml.jackson.annotation.JsonIgnoreProperties"
                )).newOnly();
        separator("top");
        separator("bottom");
    }

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_DATA_MAPPED_ROOT + this.entityCapName + "_MAPPED.java";
    }

    protected void filterThenAddImport(String... importStrings) {
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
    
    public void update() {
        this.updatePrimaryKeys();
        this.updateDeclaredColumns();
        this.updateFieldsAbleAssignByString();
        this.updateImportClass();
    }

    protected void updatePrimaryKeys() {
        PrimaryKey defaultPk = new PrimaryKey();
        defaultPk.setAutoIncrement(true);
        defaultPk.setFieldName(this.getEntity().idName());
        this.getPrimaryKeys().add(defaultPk);

        this.getEntity().getPrimaryKeyEntities().forEach(pkEntity -> {

            String pkSimpleClassName = updateConstructorParams(pkEntity);
            String pkIdName = pkEntity.idName();

            PkMap pkMap = new PkMap();
            pkMap.setIsJsonIgnored(this.getEntity().getIgnorePkJsons().contains(pkSimpleClassName));
            pkMap.setFieldName(toCamel(pkSimpleClassName));
            pkMap.setSimpleClassName(pkSimpleClassName);
            fetchAllIds(pkEntity, namedId -> {
                pkMap.getJoins().add(namedId);
                PrimaryKey pk = new PrimaryKey();
                pk.setFieldName(namedId);
                this.getPrimaryKeys().add(pk);
                return true;
            });
            this.getPkMaps().add(pkMap);

            PrimaryKey pk = new PrimaryKey();
            pk.setFieldName(pkIdName);
            this.getPrimaryKeys().add(pk);

        });
    }

    protected void updateDeclaredColumns() {
        ReflectionUtil.eachField(this.getEntity(), field -> {
            Object colObject;
            String fieldName = field.getName();
            try {
                colObject = field.get(this.getEntity());
                if (colObject instanceof MapColumn) {
                    MapColumn mapColumn = (MapColumn) colObject;
                    MapColumn.Core mapColumnCore = mapColumn.getCore();
                    mapColumnCore.setFieldName(fieldName);
                    mapColumnCore.setFieldCapName(toCapitalizeEachWord(fieldName));
                    updateMapCore(mapColumnCore);
                    this.getMapColumns().add(mapColumnCore);
                } else if (colObject instanceof Column) {
                    Column column = (Column) colObject;
                    Column.Core columnCore = column.getCore();
                    columnCore.setFieldName(fieldName);
                    updateColumnCoreInitialString(columnCore, this);
                    this.getColumns().add(columnCore);
                } else if (colObject instanceof CountMethod) {
                    CountMethod counter = (CountMethod) colObject;
                    counter.setFieldCamelName(fieldName);
                    counter.setFieldCapName(toCapitalizeEachWord(fieldName));
                    this.getCountMethods().add(counter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void updateMapCore(MapColumn.Core mapCore) {
        AbstractEntity entityMapTo = mapCore.getMapTo().getEntity();
        AbstractEntity thisEntity = this.getEntity();

        if (entityMapTo.getPrimaryKeyIdNames()
                .stream()
                .anyMatch(id -> id.equalsIgnoreCase(thisEntity.idName()))){
            mapCore.setMappedBy(toCamel(thisEntity.getClass().getSimpleName()));
        } else {
            fetchAllIds(entityMapTo, referenceIdName -> {
                String thisName = toCamel(String.format("%s-%s", mapCore.getFieldName(), referenceIdName));
                Join join = new Join(thisName, referenceIdName);
                mapCore.getJoins().add(join);
                return true;
            });
            mapCore.getJoins().forEach(join -> {
                throwErrorIfColumnNameExisted(this, join);

                Column.Core core = new Column.Core();
                core.setDataType(Long.class);
                core.setFieldName(join.getThisName());
                core.setIsUnique(mapCore.getIsUnique());
                this.getJoinIdColumns().add(core);
            });

        }
    }

    protected void updateFieldsAbleAssignByString() {
        this.setFieldsAbleAssignByString(this.columns.stream()
                .filter(Column.Core::getIsManualUpdatable)
                .filter(column -> BeanUtils.isSimpleValueType(column.getDataType()))
                .collect(Collectors.toList()));
    }

    protected void updateImportClass() {
        if (anyNullMappedBy(this)
                || !this.pkMaps.isEmpty()) {
            this.filterThenAddImport("com.fasterxml.jackson.annotation.JsonIdentityInfo",
                    "com.fasterxml.jackson.annotation.ObjectIdGenerators");
        }

        this.getEntity().getPrimaryKeyEntities()
                .forEach(entity -> this.filterThenAddImport(entity.getFullOperationClassName()));

        this.getColumns().forEach(columnCore -> {
            if (columnCore.getDefaultValue() == null) {
                if (columnCore.getDataType() == List.class) {
                    this.filterThenAddImport(List.class.getName());
                    this.filterThenAddImport(ArrayList.class.getName());
                }
            }
            if (columnCore.getDefaultValue() instanceof Enum<?>) {
                this.filterThenAddImport(columnCore.getDefaultValue().getClass().getName());
            }
            updateJavaxValidationImports(this, columnCore);
            if (columnCore.getJsonIgnore()) {
                this.filterThenAddImport("com.fasterxml.jackson.annotation.JsonIgnore");
            }
            columnCore.getPackageImports().forEach(this::filterThenAddImport);
            this.filterThenAddImport(columnCore.getDataType().getName());

        });
        this.getMapColumns().forEach(mapColumn -> {
            if (mapColumn.getMappedBy() != null) {
                this.filterThenAddImport(List.class.getName());
                this.filterThenAddImport(ArrayList.class.getName());
                if (mapColumn.getIsUnique()){
                    this.filterThenAddImport("com.fasterxml.jackson.annotation.JsonIdentityInfo",
                            "com.fasterxml.jackson.annotation.ObjectIdGenerators");
                }
            }
            this.filterThenAddImport(mapColumn.getMapTo().getEntity().getFullOperationClassName());
        });

        if (this.getFieldsAbleAssignByString().stream()
                .anyMatch(column -> column.getDataType().isAssignableFrom(java.util.Date.class))){
            this.filterThenAddImport(DateUtils.class.getName());
        }

        if (!this.getCountMethods().isEmpty() || !this.pkMaps.isEmpty() || !this.mapColumns.isEmpty()){
            this.filterThenAddImport("net.timxekhach.operation.rest.service.CommonUpdateService");
        }

    }

    private boolean anyNullMappedBy(EntityMappedModel model) {
        return  model.getMapColumns().stream()
                .map(MapColumn.Core::getMappedBy)
                .anyMatch(Objects::isNull);
    }

    private void updateJavaxValidationImports(EntityMappedModel model, Column.Core columnCore) {
        Object testNull = ObjectUtils.firstNonNull(
                columnCore.getMax(),
                columnCore.getMaxSize(),
                columnCore.getMinSize(),
                columnCore.getRegex()
        );
        boolean testBoolean = ObjectUtil.anyTrue(
                columnCore.getIsEmail(),
                columnCore.getIsNotNull(),
                columnCore.getIsPhone()
        );
        if (testNull != null || testBoolean) {
            model.filterThenAddImport("javax.validation.constraints.*");
        }
    }

    protected static void fetchAllIds(AbstractEntity pkEntity, Function<String, Boolean> consumer) {
        if (!consumer.apply(pkEntity.idName())){
            return;
        }
        pkEntity.getPrimaryKeyEntities().forEach(pk -> fetchAllIds(pk, consumer));
    }


    private void throwErrorIfColumnNameExisted(EntityMappedModel model, Join join) {
        boolean isColumnExisted = model.getColumns().stream().anyMatch(column -> column.getFieldName().equals(join.getThisName()));
        if (isColumnExisted) {
            throw new RuntimeException("column name must not be matched with join id!");
        }
    }

    protected void updateColumnCoreInitialString(Column.Core core, EntityMappedModel model) {
        if (core.getDefaultValue() == null) {
            if (core.getDataType() == List.class) {
                core.setInitialString(NEW_ARRAY_LIST);
            }
            core.setInitialString("");
        }
        if (core.getDefaultValue() instanceof String) {
            core.setInitialString(String.format(" = \"%s\"", core.getDefaultValue()));
        } else if (core.getDefaultValue() instanceof Long) {
            core.setInitialString(String.format(" = %sL", core.getDefaultValue()));
        } else if (core.getDefaultValue() instanceof Number) {
            core.setInitialString(String.format(" = %s", core.getDefaultValue()));
        } else if (core.getDefaultValue() instanceof Boolean) {
            core.setInitialString(String.format(" = %b", core.getDefaultValue()));
        } else if (core.getDefaultValue() instanceof Enum<?>) {
            model.filterThenAddImport(core.getDefaultValue().getClass().getName());
            String className = core.getDefaultValue().getClass().getSimpleName();
            String name = ((Enum<?>) core.getDefaultValue()).name();
            core.setInitialString(String.format(" = %s.%s", className, name));
        }
    }
}
