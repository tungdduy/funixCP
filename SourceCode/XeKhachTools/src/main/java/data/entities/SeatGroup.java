package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;

public class SeatGroup extends AbstractEntity {
    {
        pk(BussType.class);
    }
    Column seatGroupOrder = of(DataType.QUANTITY);
    Column seatGroupName = of(DataType.DESCRIPTION);
    Column seatGroupDesc = of(DataType.DESCRIPTION);
    Column totalSeats = of(DataType.QUANTITY);
    Column seatFrom = of(DataType.QUANTITY);
}
