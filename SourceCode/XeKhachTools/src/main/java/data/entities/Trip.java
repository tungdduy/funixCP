package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;
import util.constants.TripStatus;

import static data.entities.abstracts.DataType.MONEY;
import static data.entities.abstracts.DataType.TIME_ONLY;

public class Trip extends AbstractEntity {
    {
        pk(BussSchedule.class);
    }

    Column price = of(MONEY);
    Column status = status(TripStatus.class);
    Column startTime = of(TIME_ONLY);

    MapColumn tripUsers = map(TripUser.class);

}

