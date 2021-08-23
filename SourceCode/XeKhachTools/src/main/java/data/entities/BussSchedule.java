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

    MapColumn path = map(Path.class);
    Column price = of(MONEY);
    Column launchTime = of(TIME_ONLY);
    Column effectiveDateFrom = of(DATE_ONLY);
    Column jsonBussSchedulePoints = of(LONG_TEXT);

    Column workingDays = of(DESCRIPTION);

    MapColumn startPoint = map(PathPoint.class);
    MapColumn endPoint = map(PathPoint.class);
}
