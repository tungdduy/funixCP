package generator.abstracts.models;

import data.entities.abstracts.AbstractEntity;
import generator.java.data.entity.Param;
import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import javax.xml.ws.Holder;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class AbstractEntityModel<E extends AbstractEntity> extends AbstractModel{
    protected String entityClassName;
    protected E entity;
    protected List<Param> constructorParams = new ArrayList<>();

    public static Holder<AbstractEntity> entityHolder = new Holder<>();

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        this.entity = (E) entityHolder.value;
        this.entityClassName = this.entity.getClass().getSimpleName();
        logger.info("fetch and set Separator Content for " + this.getClass().getSimpleName() + entity.getClass().getSimpleName());
        super.init();
    }

    protected String
            bodyContent,
            importContent,
            bodySeparator = StringUtil.buildSeparator("BODY"),
            importSeparator = StringUtil.buildSeparator("IMPORT");

}
