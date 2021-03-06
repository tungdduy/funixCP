package generator.abstracts.models;

import data.entities.abstracts.AbstractEntity;
import generator.java.data.entity.Param;
import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import javax.xml.ws.Holder;
import java.util.ArrayList;
import java.util.List;

import static util.StringUtil.toCamel;

@Getter @Setter
public abstract class AbstractEntityModel<E extends AbstractEntity> extends AbstractModel{

    protected E entity;
    protected String entityCapName;
    protected String entityCamelName;
    protected List<Param> constructorParams = new ArrayList<>();

    public static Holder<AbstractEntity> entityHolder = new Holder<>();

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        this.entity = (E) entityHolder.value;
        this.entityCapName = this.entity.getClass().getSimpleName();
        this.entityCamelName = toCamel(this.entityCapName);
        super.init();
    }

    protected String updateConstructorParams(AbstractEntity pkEntity) {
        String pkSimpleClassName = pkEntity.getClass().getSimpleName();
        Param param = new Param(pkSimpleClassName, toCamel(pkSimpleClassName));
        this.getConstructorParams().add(param);
        return pkSimpleClassName;
    }

}
