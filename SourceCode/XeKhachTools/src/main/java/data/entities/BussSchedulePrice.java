package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;
import data.models.MapColumn;

public class BussSchedulePrice extends AbstractEntity {
    {
        pk(BussSchedule.class);
    }
    MapColumn pointFrom = map(BussSchedulePoint.class);
    MapColumn pointTo = map(BussSchedulePoint.class);
    Column price = of(DataType.MONEY);
}
