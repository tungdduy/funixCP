package generator.java.data.mapped;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import data.models.PrimaryKey;
import generator.abstracts.render.AbstractEntityRender;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import util.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static util.StringUtil.toCamel;
import static util.StringUtil.toIdName;

public class EntityMappedRender extends AbstractEntityRender<EntityMappedModel> {

    public static final String NEW_ARRAY_LIST = " = new ArrayList<>()";

    @Override
    protected void handleModel(EntityMappedModel model) {
        updatePrimaryKeys(model);
        updateDeclaredColumns(model);
        updateImportClass(model);
    }

    private void updateImportClass(EntityMappedModel model) {

        model.getEntity().getPrimaryKeyEntities().forEach(entity -> {
            model.getImports().add(entity.getFullOperationClassName());
        });

        model.getColumns().forEach(columnCore -> {
            if (columnCore.getDefaultValue() == null) {
                if (columnCore.getDataType() == List.class) {
                    model.getImports().add(List.class.getName());
                    model.getImports().add(ArrayList.class.getName());
                }
            }
            if (columnCore.getDefaultValue() instanceof Enum<?>) {
                model.getImports().add(columnCore.getDefaultValue().getClass().getName());
            }
            Object testNull = ObjectUtils.firstNonNull(columnCore.getMax(),
                    columnCore.getMaxSize(),
                    columnCore.getMinSize(),
                    columnCore.getRegex(),
                    columnCore.getIsEmail(),
                    columnCore.getIsPhone(),
                    columnCore.getIsNotNull()
                    );
            if (testNull != null) {
                model.getImports().add("javax.validation.constraints.*");
            }
            model.filterThenAddImport(columnCore.getDataType().getName());

        });
        model.getMapColumns().forEach(mapColumn -> {
            if (mapColumn.getMappedBy() != null) {
                model.getImports().add(List.class.getName());
                model.getImports().add(ArrayList.class.getName());
            }

            model.getImports().add(mapColumn.getMapTo().getEntity().getFullOperationClassName());
        });
    }

    private void updatePrimaryKeys(EntityMappedModel model) {
        PrimaryKey defaultPk = new PrimaryKey();
        defaultPk.setAutoIncrement(true);
        defaultPk.setFieldName(toIdName(model.getEntity()));
        model.getPrimaryKeys().add(defaultPk);

        model.getEntity().getPrimaryKeyEntities().forEach(pkEntity -> {

            String pkSimpleClassName = updateConstructorParams(model, pkEntity);
            String pkIdName = toIdName(pkEntity);

            PkMap pkMap = new PkMap();
            pkMap.setFieldName(toCamel(pkSimpleClassName));
            pkMap.setSimpleClassName(pkSimpleClassName);
            fetchAllIds(pkEntity, namedId -> {
                pkMap.getJoins().add(namedId);
                PrimaryKey pk = new PrimaryKey();
                pk.setFieldName(namedId);
                model.getPrimaryKeys().add(pk);
            });
            model.getPkMaps().add(pkMap);

            PrimaryKey pk = new PrimaryKey();
            pk.setFieldName(pkIdName);
            model.getPrimaryKeys().add(pk);

        });
    }

    private void fetchAllIds(AbstractEntity pkEntity, Consumer<String> consumer) {
        consumer.accept(toIdName(pkEntity));
        pkEntity.getPrimaryKeyEntities().forEach(pk -> fetchAllIds(pk, consumer));
    }

    private void updateDeclaredColumns(EntityMappedModel model) {
        ReflectionUtil.eachField(model.getEntity(), field -> {
            Object colObject = null;
            String fieldName = field.getName();
            try {
                colObject = field.get(model.getEntity());
                if (colObject instanceof MapColumn) {
                    MapColumn mapColumn = (MapColumn) colObject;
                    MapColumn.Core mapColumnCore = mapColumn.getCore();
                    mapColumnCore.setFieldName(fieldName);
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
        String thisEntityIdName = toIdName(thisEntity);
        entityMapTo.getPrimaryKeyEntities().forEach(pkEntity -> {
            String referenceIdName = toIdName(pkEntity);
            if (referenceIdName.equals(thisEntityIdName)) {
                entityMapToHasPkToThis.setValue(true);
            } else {
                String thisName = toCamel(String.format("%s-%s", mapCore.getFieldName(), referenceIdName));
                Join join = new Join(thisName, referenceIdName);
                mapCore.getJoins().add(join);
            }

        });

        if (entityMapToHasPkToThis.isTrue()) {
            mapCore.setMappedBy(toCamel(thisEntity.getClass().getSimpleName()));
        } else {
            mapCore.getJoins().forEach(join -> {
                throwErrorIfColumnNameExisted(model, join);

                Column.Core core = new Column.Core();
                core.setDataType(Long.class);
                core.setFieldName(join.getThisName());

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
            core.setInitialString(String.format(" = %f", (Double) core.getDefaultValue()));
        } else if (core.getDefaultValue() instanceof Boolean) {
            core.setInitialString(String.format(" = %b", (Boolean) core.getDefaultValue()));
        } else if (core.getDefaultValue() instanceof Enum<?>) {
            model.getImports().add(core.getDefaultValue().getClass().getName());
            String className = core.getDefaultValue().getClass().getName();
            String name = ((Enum<?>) core.getDefaultValue()).name();
            core.setInitialString(String.format(" = %s.%s", className, name));
        }
    }
}
