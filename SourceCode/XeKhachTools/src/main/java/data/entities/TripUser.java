package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import util.constants.TripUserStatus;

import static data.entities.abstracts.DataType.*;
import static util.constants.TripUserStatus.PENDING;

public class TripUser extends AbstractEntity {
    {
        pk(Trip.class);
        jpaOrderString("OrderByTripUserIdDesc");
    }

    MapColumn user = map(User.class);
    Column phoneNumber = of(PHONE);
    Column fullName = of(DESCRIPTION);
    Column email = of(EMAIL);

    Column status = status(TripUserStatus.class).defaultValue(PENDING);
    Column unitPrice = of(MONEY);
    Column totalPrice = of(MONEY);
    Column tripUserPointsString = of(COMMA_SEPARATOR);
    Column seatsString = of(COMMA_SEPARATOR);
    MapColumn confirmedBy = map(Employee.class);
    Column confirmedDateTime = of(DATE_TIME);

}
