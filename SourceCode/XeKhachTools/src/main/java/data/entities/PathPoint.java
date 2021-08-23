package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;

import javax.persistence.OrderBy;

public class PathPoint extends AbstractEntity {
    {
        pk(Path.class);
        pk(Location.class);
        jpaOrderString("OrderByPointOrderAsc");
    }
    Column pointName = of(DataType.DESCRIPTION);
    Column pointDesc = of(DataType.DESCRIPTION);
    Column pointOrder = of(DataType.QUANTITY);
}
