package generator.abstracts.render;

import data.entities.abstracts.AbstractEntity;
import generator.abstracts.models.AbstractEntityModel;
import generator.java.data.entity.Param;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static util.ReflectionUtil.newInstancesOfAllChildren;
import static util.StringUtil.toCamel;

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
    public static void renderWithParent() {
        String packageName = StringUtil.removeLastChar(AbstractEntity.class.getPackage().getName(), ".abstracts".length());
        List<? extends AbstractEntity> entities = newInstancesOfAllChildren(AbstractEntity.class, packageName);
        Consumer<AbstractEntity> visitEntity = entity -> renders.forEach(render -> render.visit(entity));
        entities.forEach(visitEntity);
        renders.forEach(AbstractRender::executeRenders);
    }

    public static void standaloneRender() {
        batchNewAllChildrenRenders(AbstractEntityRender.class);
        renderWithParent();
    }

    private <T extends AbstractEntity> void visit(T entity) {
        this.prepareNewModelThenForwardHandle(entity);
    }

    private <T extends AbstractEntity> void prepareNewModelThenForwardHandle(T entity) {
        AbstractEntityModel.entityHolder.value = entity;
        E model = newModel();
        this.getModelFiles().add(model);
        handleModel(model);
    }

    protected String updateConstructorParams(AbstractEntityModel model, AbstractEntity pkEntity) {
        String pkSimpleClassName = pkEntity.getClass().getSimpleName();
        Param param = new Param(pkSimpleClassName, toCamel(pkSimpleClassName));
        model.getConstructorParams().add(param);
        return pkSimpleClassName;
    }
}
