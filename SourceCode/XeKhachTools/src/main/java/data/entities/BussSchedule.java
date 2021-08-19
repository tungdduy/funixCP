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
    }

    Column price = of(MONEY);
    Column launchTime = of(TIME_ONLY);
    Column effectiveDateFrom = of(DATE_ONLY);

    Column workingDays = of(DESCRIPTION);

    MapColumn startPoint = map(BussPoint.class);
    MapColumn middlePoints = map(BussSchedulePoint.class);
    MapColumn endPoint = map(BussPoint.class);
}
