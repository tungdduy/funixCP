package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;

import static data.entities.abstracts.DataType.*;

@Getter @Setter
public class BussTrip extends AbstractEntity {
    {
        pk(Buss.class);
        pk(Company.class);
    }
    Column launchTime = of(TIME_ONLY);
    Column effectiveDateFrom = of(DATE_ONLY);
    Column price = of(MONEY);

    Column sunday = of(FALSE);
    Column monday = of(FALSE);
    Column tuesday = of(FALSE);
    Column wednesday = of(FALSE);
    Column thursday = of(FALSE);
    Column friday = of(FALSE);
    Column saturday = of(FALSE);

    MapColumn tripPoints = map(TripPoint.class);

    MapColumn startPoint = map(BussPoint.class);
    MapColumn endPoint = map(BussPoint.class);
}
