package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;
import util.constants.TripUserStatus;

import static data.entities.abstracts.DataType.MONEY;
import static util.constants.TripUserStatus.PENDING;

@Getter
@Setter
public class TripUser extends AbstractEntity {
    {
        pk(Trip.class);
        pk(User.class);
    }
    Column totalPrice = of(MONEY);
    Column status = status(TripUserStatus.class).defaultValue(PENDING);
    MapColumn confirmedBy = map(Employee.class);
}
