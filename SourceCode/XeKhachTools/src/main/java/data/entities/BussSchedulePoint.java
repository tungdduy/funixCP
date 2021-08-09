package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;
import data.models.MapColumn;

public class BussSchedulePoint extends AbstractEntity {
    {
        pk(BussSchedule.class);
        pk(BussPoint.class);
    }
    Column priceToEndPoint = of(DataType.MONEY);
}
