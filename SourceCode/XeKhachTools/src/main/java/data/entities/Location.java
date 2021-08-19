package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;
import data.models.MapColumn;

public class Location extends AbstractEntity {
    MapColumn parent = map(Location.class);
    Column locationName = of(DataType.DESCRIPTION);
}
