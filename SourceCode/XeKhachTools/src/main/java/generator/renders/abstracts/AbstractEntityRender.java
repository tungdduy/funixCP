package generator.renders.abstracts;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import data.models.Pk;
import generator.models.abstracts.AbstractEntityModel;
import util.ReflectionUtil;
import util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static util.ReflectionUtil.newInstancesOfAllChildren;

@SuppressWarnings("all")
public abstract class AbstractEntityRender<E extends AbstractEntityModel> extends AbstractRender<E> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected boolean isNewModelOnInit() {
        return false;
    }

    @Override
    protected boolean isManualRender() {
        return true;
    }

    private static List<AbstractEntityRender> renders = new ArrayList<>();

    protected AbstractEntityRender() {
        renders.add(this);
    }

    @SuppressWarnings("rawtypes")
    public static void render() {
        String packageName = StringUtil.removeLastChar(AbstractEntity.class.getPackage().getName(), ".abstracts".length());
        List<? extends AbstractEntity> entities = newInstancesOfAllChildren(AbstractEntity.class, packageName);
        Consumer<AbstractEntity> visitEntity = entity -> renders.forEach(render -> render.visit(entity));
        entities.forEach(visitEntity);
        renders.forEach(AbstractRender::executeRenders);
    }

    protected List<Column> columns = new ArrayList<>();
    protected List<Pk> pks = new ArrayList<>();
    protected List<MapColumn> mapColumns = new ArrayList<>();

    private <T extends AbstractEntity> void visit(T entity) {
        this.updateColumnFieldName(entity);
        this.newModelThenForwardHandle(entity);
    }

    /* Map<String, String> = <className.methodClassName, pkFieldName> */
    public static final Map<String, String> entitiesMap= new HashMap<>();

    private <T extends AbstractEntity, C extends Column> void updateColumnFieldName(T entity) {
        this.columns.clear();
        this.pks.clear();
        this.mapColumns.clear();
//        entitiesMap.put(entity.getClass(), entity);
        ReflectionUtil.eachField(entity, field -> {
            Object colObject = null;
            try {
                colObject = field.get(entity);
                if (colObject instanceof MapColumn) {
                    MapColumn mapColumn = (MapColumn) colObject;
                    mapColumn.getCore().setFieldName(field.getName());
                    this.mapColumns.add(mapColumn);
                } else if (colObject instanceof Pk) {
                    Pk pk = (Pk) colObject;
                    pk.setFieldName(field.getName());
                    this.pks.add(pk);
                } else if (colObject instanceof Column) {
                    Column column = (Column) colObject;
                    column.getCore().setFieldName(field.getName());
                    this.columns.add((Column) colObject);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private <T extends AbstractEntity> void newModelThenForwardHandle(T entity) {
        E model = newModel();
        this.getModelFiles().add(model);
        model.setEntityClassName(entity.getClass().getSimpleName());
        handleModel(model);
    }
}
