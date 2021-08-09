package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;

public class TripUserSeat extends AbstractEntity {
    {
        pk(TripUser.class);
    }
    Column seatNo = of(DataType.QUANTITY);
}
