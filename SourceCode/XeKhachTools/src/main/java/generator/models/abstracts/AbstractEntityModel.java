package generator.models.abstracts;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityModel extends AbstractModel {
    protected String entityClassName;
}
