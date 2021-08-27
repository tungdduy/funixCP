package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.CountMethod;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;
import util.constants.TripStatus;

import static data.entities.abstracts.DataType.*;

public class Trip extends AbstractEntity {
    {
        pk(BussSchedule.class);
    }

    CountMethod totalTripUsers = count(TripUser.class);
    Column lockedSeatsString = of(COMMA_SEPARATOR);
    Column tripUnitPrice = of(MONEY);
    Column launchTime = of(TIME_ONLY);
    Column launchDate = of(DATE_ONLY);
    MapColumn tripUsers = map(TripUser.class);

}

