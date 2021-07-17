package generator.models.abstracts;

import data.entities.abstracts.AbstractEntity;
import generator.models.sub.Param;
import generator.models.interfaces.SeparatorContent;
import lombok.Getter;
import lombok.Setter;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class AbstractEntityModel<E extends AbstractEntity> extends AbstractModel implements SeparatorContent {
    protected String entityClassName;
    protected E entity;
    protected List<Param> constructorParams = new ArrayList<>();

    String  bodyContent = "",
            importContent = "",
            bodySeparator = StringUtil.buildSeparator("BODY"),
            importSeparator = StringUtil.buildSeparator("IMPORT");

}
