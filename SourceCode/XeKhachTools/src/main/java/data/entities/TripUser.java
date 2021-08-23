package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.CountMethod;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;
import util.constants.TripUserStatus;

import static data.entities.abstracts.DataType.*;
import static util.constants.TripUserStatus.PENDING;

public class TripUser extends AbstractEntity {
    {
        pk(Trip.class);
        pk(User.class);
    }
    Column status = status(TripUserStatus.class).defaultValue(PENDING);
    Column totalPrice = of(MONEY);
    Column seats = of(DESCRIPTION);
    MapColumn confirmedBy = map(Employee.class);
    Column confirmedDateTime = of(DATE_TIME);

}
