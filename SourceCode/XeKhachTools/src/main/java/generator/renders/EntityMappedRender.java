package generator.renders;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import data.models.PrimaryKey;
import generator.models.EntityMappedModel;
import generator.models.sub.Join;
import generator.models.sub.Param;
import generator.models.sub.PkMap;
import generator.renders.abstracts.AbstractEntityRender;
import org.apache.commons.lang3.mutable.MutableBoolean;
import util.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static util.StringUtil.toCamel;
import static util.StringUtil.toIdName;

public class EntityMappedRender extends AbstractEntityRender<EntityMappedModel> {

    @Override
    protected void handleModel(EntityMappedModel model) {
        updatePrimaryKeys(model);
        updateDeclaredColumns(model);
    }

    private void updatePrimaryKeys(EntityMappedModel model) {
        PrimaryKey defaultPk = new PrimaryKey();
        defaultPk.setAutoIncrement(true);
        defaultPk.setFieldName(toIdName(model.getEntity()));
        model.getPrimaryKeys().add(defaultPk);

        model.getEntity().getPrimaryKeyEntities().forEach(pkEntity -> {
            String idName = toIdName(pkEntity);

            PrimaryKey pk = new PrimaryKey();
            pk.setFieldName(idName);
            model.getPrimaryKeys().add(pk);

            String pkSimpleClassName = pkEntity.getClass().getSimpleName();
            Param param = new Param(pkSimpleClassName, toCamel(pkSimpleClassName));
            model.getConstructorParams().add(param);

            PkMap pkMap = new PkMap();
            pkMap.setFieldName(idName);
            pkMap.setSimpleClassName(pkSimpleClassName);
            fetchAllIds(pkEntity, pkMap.getJoins()::add);
            model.getPkMaps().add(pkMap);
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
                    updateMapCore(mapColumn.getCore(), model);
                    mapColumnCore.setFieldName(fieldName);
                    model.getMapColumns().add(mapColumnCore);
                } else if (colObject instanceof Column) {
                    Column column = (Column) colObject;
                    Column.Core columnCore = column.getCore();
                    columnCore.setFieldName(fieldName);
                    updateColumnCore(columnCore);
                    model.getColumns().add(column.getCore());
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
        List<String> mapToIds = new ArrayList<>();
        fetchAllIds(entityMapTo, referenceIdName -> {
            if (referenceIdName.equals(toIdName(thisEntity))) {
                entityMapToHasPkToThis.setValue(true);
            } else {
                String newIdFromThisMap = toCamel(String.format("%s-%s", mapCore.getFieldName(), referenceIdName));
                Join join = new Join(newIdFromThisMap, referenceIdName);
                mapCore.getJoins().add(join);
            }
        });

        if (!entityMapToHasPkToThis.booleanValue()) {
            mapCore.setMappedBy(toCamel(thisEntity.getClass().getSimpleName()));
            mapCore.setInitialString(" = new ArrayList<>()");
        } else {
            addJoinColumnWithMapToEntity(mapCore, model);
        }
    }

    private void addJoinColumnWithMapToEntity(MapColumn.Core mapCore, EntityMappedModel model) {
        mapCore.getJoins().forEach(join -> {
            Column.Core core = new Column.Core();
            core.setFieldName(join.getThisName());
            core.setDataType(Long.class);
            boolean isIdExisted = model.getColumns().stream().anyMatch(column -> column.getFieldName().equals(core.getFieldName()));
            if (isIdExisted) {
                throw new RuntimeException("column name must not be matched with join id!");
            }
            model.getColumns().add(core);
        });
    }

    public void updateColumnCore(Column.Core core) {

        if (core.getDefaultValue() == null) {
            if (core.getDataType() == List.class) {
                core.getImports().add(List.class.getName());
                core.getImports().add(ArrayList.class.getName());
                core.setInitialString(" = new ArrayList<>()");
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
            core.getImports().add(core.getDefaultValue().getClass().getName());
            String simpleClassName = core.getDefaultValue().getClass().getSimpleName();
            String name = ((Enum<?>) core.getDefaultValue()).name();
            core.setInitialString(String.format(" = %s.%s", name, simpleClassName));
        }
    }

}
