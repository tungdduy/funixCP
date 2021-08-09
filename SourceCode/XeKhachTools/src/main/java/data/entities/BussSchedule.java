package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;

import static data.entities.abstracts.DataType.*;

public class BussSchedule extends AbstractEntity {
    {
        pk(Buss.class);
        pk(Company.class);
    }

    Column price = of(MONEY);
    Column launchTime = of(TIME_ONLY);
    Column effectiveDateFrom = of(DATE_ONLY);

    Column sunday = of(FALSE);
    Column monday = of(FALSE);
    Column tuesday = of(FALSE);
    Column wednesday = of(FALSE);
    Column thursday = of(FALSE);
    Column friday = of(FALSE);
    Column saturday = of(FALSE);

    MapColumn startPoint = map(BussPoint.class);
    MapColumn middlePoints = map(BussSchedulePoint.class);
    MapColumn endPoint = map(BussPoint.class);
}
