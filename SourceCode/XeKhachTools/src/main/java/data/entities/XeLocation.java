package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;

import static data.entities.abstracts.DataType.DESCRIPTION;

public class XeLocation extends AbstractEntity {
    Column locationName = of(DESCRIPTION);
    MapColumn parent = map(XeLocation.class);
}
