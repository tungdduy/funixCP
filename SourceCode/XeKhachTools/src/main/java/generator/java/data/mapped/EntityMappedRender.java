package generator.java.data.mapped;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import data.models.PrimaryKey;
import generator.abstracts.render.AbstractEntityRender;
import net.timxekhach.operation.response.ErrorCode;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import util.ObjectUtil;
import util.ReflectionUtil;
import util.StringUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static util.StringUtil.toCamel;
import static util.StringUtil.toCapitalizeEachWord;

@SuppressWarnings("unused")
public class EntityMappedRender extends AbstractEntityRender<EntityMappedModel> {

    public static final String NEW_ARRAY_LIST = " = new ArrayList<>()";

    @Override
    public void runBeforeRender() {
        this.getModelFiles().forEach(EntityMappedModel::updateCountOnInsertDelete);
    }

    @Override
    protected void handleModel(EntityMappedModel model) {
        updatePrimaryKeys(model);
        updateDeclaredColumns(model);
        updateFieldsAbleAssignByString(model);
        updateImportClass(model);
    }

    private void updateFieldsAbleAssignByString(EntityMappedModel model) {
        model.setFieldsAbleAssignByString(model.columns.stream()
                .filter(Column.Core::getIsManualUpdatable)
                .filter(column -> BeanUtils.isSimpleValueType(column.getDataType()))
                .collect(Collectors.toList()));
    }

    private void updateImportClass(EntityMappedModel model) {
        if (anyNullMappedBy(model)
                || !model.pkMaps.isEmpty()) {
            model.filterThenAddImport("com.fasterxml.jackson.annotation.JsonIgnore");
        }

        model.getEntity().getPrimaryKeyEntities()
                .forEach(entity -> model.getImports()
                        .add(entity.getFullOperationClassName()));

        model.getColumns().forEach(columnCore -> {
            if (columnCore.getDefaultValue() == null) {
                if (columnCore.getDataType() == List.class) {
                    model.filterThenAddImport(List.class.getName());
                    model.filterThenAddImport(ArrayList.class.getName());
                }
            }
            if (columnCore.getDefaultValue() instanceof Enum<?>) {
                model.filterThenAddImport(columnCore.getDefaultValue().getClass().getName());
            }
            updateJavaxValidationImports(model, columnCore);
            if (columnCore.getJsonIgnore()) {
               model.filterThenAddImport("com.fasterxml.jackson.annotation.JsonIgnore");
            }
            model.filterThenAddImport(columnCore.getDataType().getName());

        });
        model.getMapColumns().forEach(mapColumn -> {
            if (mapColumn.getMappedBy() != null) {
                model.filterThenAddImport(List.class.getName());
                model.filterThenAddImport(ArrayList.class.getName());
            }

            model.filterThenAddImport(mapColumn.getMapTo().getEntity().getFullOperationClassName());
        });

        if (model.getFieldsAbleAssignByString().stream()
                .anyMatch(column -> column.getDataType().isAssignableFrom(java.util.Date.class))){
            model.filterThenAddImport(DateUtils.class.getName());
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

    private void updatePrimaryKeys(EntityMappedModel model) {
        PrimaryKey defaultPk = new PrimaryKey();
        defaultPk.setAutoIncrement(true);
        defaultPk.setFieldName(model.getEntity().idName());
        model.getPrimaryKeys().add(defaultPk);

        model.getEntity().getPrimaryKeyEntities().forEach(pkEntity -> {

            String pkSimpleClassName = updateConstructorParams(model, pkEntity);
            String pkIdName = pkEntity.idName();

            PkMap pkMap = new PkMap();
            pkMap.setFieldName(toCamel(pkSimpleClassName));
            pkMap.setSimpleClassName(pkSimpleClassName);
            fetchAllIds(pkEntity, namedId -> {
                pkMap.getJoins().add(namedId);
                PrimaryKey pk = new PrimaryKey();
                pk.setFieldName(namedId);
                model.getPrimaryKeys().add(pk);
                return true;
            });
            model.getPkMaps().add(pkMap);

            PrimaryKey pk = new PrimaryKey();
            pk.setFieldName(pkIdName);
            model.getPrimaryKeys().add(pk);

        });
    }

    private void fetchAllIds(AbstractEntity pkEntity, Function<String, Boolean> consumer) {
        if (!consumer.apply(pkEntity.idName())){
           return;
        }
        pkEntity.getPrimaryKeyEntities().forEach(pk -> fetchAllIds(pk, consumer));
    }

    private void updateDeclaredColumns(EntityMappedModel model) {
        ReflectionUtil.eachField(model.getEntity(), field -> {
            Object colObject;
            String fieldName = field.getName();
            try {
                colObject = field.get(model.getEntity());
                if (colObject instanceof MapColumn) {
                    MapColumn mapColumn = (MapColumn) colObject;
                    MapColumn.Core mapColumnCore = mapColumn.getCore();
                    mapColumnCore.setFieldName(fieldName);
                    mapColumnCore.setFieldCapName(toCapitalizeEachWord(fieldName));
                    updateMapCore(mapColumnCore, model);
                    model.getMapColumns().add(mapColumnCore);
                } else if (colObject instanceof Column) {
                    Column column = (Column) colObject;
                    Column.Core columnCore = column.getCore();
                    columnCore.setFieldName(fieldName);
                    updateColumnCoreInitialString(columnCore, model);
                    model.getColumns().add(columnCore);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



    private void updateMapCore(MapColumn.Core mapCore, EntityMappedModel model) {
        AbstractEntity entityMapTo = mapCore.getMapTo().getEntity();
        AbstractEntity thisEntity = model.getEntity();

        MutableBoolean entityMapToHasPkToThis = new MutableBoolean(false);

        fetchAllIds(entityMapTo, referenceIdName -> {
            if(referenceIdName.equals(thisEntity.idName())
             && !thisEntity.idName().equals(entityMapTo.idName()) ) {
                entityMapToHasPkToThis.setValue(true);
                return false;
            }
            String thisName = toCamel(String.format("%s-%s", mapCore.getFieldName(), referenceIdName));
            Join join = new Join(thisName, referenceIdName);
            mapCore.getJoins().add(join);
            return true;
        });

        if (entityMapToHasPkToThis.isTrue()) {
            mapCore.setMappedBy(toCamel(thisEntity.getClass().getSimpleName()));
            if (mapCore.getHasCountField()) {
                model.addToAllCountOnInsertDeletes(mapCore);
            }
        } else {
            mapCore.getJoins().forEach(join -> {
                throwErrorIfColumnNameExisted(model, join);

                Column.Core core = new Column.Core();
                core.setDataType(Long.class);
                core.setFieldName(join.getThisName());
                core.setIsUnique(mapCore.getIsUnique());
                model.getJoinIdColumns().add(core);
            });

        }
    }


    private void throwErrorIfColumnNameExisted(EntityMappedModel model, Join join) {
        boolean isColumnExisted = model.getColumns().stream().anyMatch(column -> column.getFieldName().equals(join.getThisName()));
        if (isColumnExisted) {
            throw new RuntimeException("column name must not be matched with join id!");
        }
    }

    public void updateColumnCoreInitialString(Column.Core core, EntityMappedModel model) {
        if (core.getDefaultValue() == null) {
            if (core.getDataType() == List.class) {
                core.setInitialString(NEW_ARRAY_LIST);
            }
            core.setInitialString("");
        }
        if (core.getDefaultValue() instanceof String) {
            core.setInitialString(String.format(" = \"%s\"", core.getDefaultValue()));
        } else if (core.getDefaultValue() instanceof Number) {
            core.setInitialString(String.format(" = %s", core.getDefaultValue()));
        } else if (core.getDefaultValue() instanceof Boolean) {
            core.setInitialString(String.format(" = %b", core.getDefaultValue()));
        } else if (core.getDefaultValue() instanceof Enum<?>) {
            model.filterThenAddImport(core.getDefaultValue().getClass().getName());
            String className = core.getDefaultValue().getClass().getName();
            String name = ((Enum<?>) core.getDefaultValue()).name();
            core.setInitialString(String.format(" = %s.%s", className, name));
        }
    }
}
