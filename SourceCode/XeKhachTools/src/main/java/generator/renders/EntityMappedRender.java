package generator.renders;

import data.models.Column;
import data.models.MapColumn;
import data.models.Pk;
import generator.models.EntityMappedModel;
import generator.models.sub.Constructor;
import generator.models.sub.Join;
import generator.models.sub.Param;
import generator.renders.abstracts.AbstractEntityRender;
import org.apache.commons.lang3.mutable.MutableInt;
import util.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static util.ReflectionUtil.findFieldsByType;

public class EntityMappedRender extends AbstractEntityRender<EntityMappedModel> {

    @Override
    protected void handleModel(EntityMappedModel model) {
        this.columns.forEach(column -> {
            model.setColumns(this.columns
                    .stream()
                    .map(Column::getCore)
                    .peek(this::updateColumn)
                    .collect(Collectors.toList())
            );
        });

        this.mapColumns.forEach(mapper -> {
            model.setMapColumns(this.mapColumns
                    .stream()
                    .map(MapColumn::getCore)
                    .peek(this::updateMapColumn)
                    .collect(Collectors.toList())
            );
        });

        List<Param> params = new ArrayList<>();
        MutableInt longPkCount = new MutableInt(0);
        this.pks.forEach(pk -> {
            if (exceedNoOfLongPk(longPkCount, pk)) return;
            Param param = new Param();
            param.setName(pk.getFieldName());
            param.setClassName(pk.getSimpleClassName());
            params.add(param);
        });

        if (!params.isEmpty()) {
            model.getConstructors().add(new Constructor(params));
        }

        model.getConstructors().add(new Constructor());
    }

    private boolean exceedNoOfLongPk(MutableInt longPkCount, data.models.Pk pk) {
        if (pk.getDataType() == Long.class) {
            if (longPkCount.getValue() > 0) {
                throw new RuntimeException("only 1 pk able to has type Long.class!");
            }
            longPkCount.add(1);
            return true;
        }
        return false;
    }

    private void updateMapColumn(MapColumn.Core mapCore) {
        updateManyToOne(mapCore);
        updateOneToMany(mapCore);
    }

    private void updateOneToMany(MapColumn.Core mapCore) {
        if (mapCore.getPk() != null) {
            // TODO: updateOneToMany
        }
    }

    private void updateManyToOne(MapColumn.Core mapCore) {
        if (mapCore.getEntityClass() != null) {
            // TODO: updateManyToOne
            List<Pk> longPks = buildLongPks(mapCore);
            mapCore.setJoins(longPks.stream()
                    .map(Join::new)
                    .collect(Collectors.toList()));
            mapCore.setInitialString(" = new ArrayList<>()");
            mapCore.setSimpleClassName("Long");
        }
    }

    private List<Pk> buildLongPks(MapColumn.Core mapCore) {
        List<Pk> longPks = new ArrayList<>();
        fetchLongPk(mapCore.getEntityClass(), longPks);
        return longPks;
    }

    private void fetchLongPk(Class<?> dataType, List<Pk> longPks) {
        List<Pk> entityPks = findFieldsByType(dataType, Pk.class,
                (field, pk) ->  pk.setFieldName(field.getName())
        );
        entityPks.forEach(pk -> {
            if (pk.getDataType() == Long.class) {
                longPks.add(pk);
            } else {
                fetchLongPk(pk.getDataType(), longPks);
            }
        });
    }

    public void updateColumn(Column.Core core) {
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
