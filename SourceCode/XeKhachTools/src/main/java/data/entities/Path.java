package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;
import data.models.CountMethod;
import data.models.MapColumn;

public class Path extends AbstractEntity {
    {
        pk(Company.class);
    }
    CountMethod totalPathPoints = count(PathPoint.class);
    Column pathName = of(DataType.DESCRIPTION);
    Column pathDesc = of(DataType.DESCRIPTION);
    MapColumn pathPoints = map(PathPoint.class).orderBy("pointOrder ASC");

}
