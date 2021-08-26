package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.CountMethod;
import data.models.MapColumn;

import static data.entities.abstracts.DataType.*;

public class BussSchedule extends AbstractEntity {
    {
        pk(Buss.class);
    }

    MapColumn path = map(Path.class);
    Column scheduleUnitPrice = of(MONEY);
    Column launchTime = of(TIME_ONLY);
    Column effectiveDateFrom = of(DATE_ONLY);
    MapColumn bussSchedulePoints = map(BussSchedulePoint.class);
    CountMethod totalBussSchedulePoints = count(BussSchedulePoint.class);
    Column workingDays = of(DESCRIPTION);

    MapColumn startPoint = map(PathPoint.class);
    MapColumn endPoint = map(PathPoint.class);

}
