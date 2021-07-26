package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;
import data.models.MapColumn;

public class BussType extends AbstractEntity {
    Column bussTypeName = of(DataType.DESCRIPTION);
    Column bussTypeDesc = of(DataType.DESCRIPTION);

    MapColumn allSeatTypes = map(SeatType.class);
}
