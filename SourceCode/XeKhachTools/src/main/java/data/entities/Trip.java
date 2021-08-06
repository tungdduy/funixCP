package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;
import util.constants.TripStatus;

import static data.entities.abstracts.DataType.MONEY;
import static data.entities.abstracts.DataType.TIME_ONLY;

@Getter
@Setter
public class Trip extends AbstractEntity {
    {
        pk(Buss.class);
    }

    Column price = of(MONEY);
    Column status = status(TripStatus.class);
    Column startTime = of(TIME_ONLY);

    MapColumn allTripUserSeats = map(TripUserSeat.class);
    MapColumn startPoint = map(BussPoint.class);
    MapColumn endPoint = map(BussPoint.class);
    MapColumn tripUsers = map(TripUser.class);

}

