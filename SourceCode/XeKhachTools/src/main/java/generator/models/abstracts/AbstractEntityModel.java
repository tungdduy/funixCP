package generator.models.abstracts;

import data.entities.abstracts.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityModel<E extends AbstractEntity> extends AbstractModel {
    protected String entityClassName;
    protected E entity;
}
