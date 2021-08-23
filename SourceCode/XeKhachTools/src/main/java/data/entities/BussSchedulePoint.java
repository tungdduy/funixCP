package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;

public class BussSchedulePoint extends AbstractEntity {
    {
        pk(BussSchedule.class);
        pk(PathPoint.class);
    }
    Column price = of(DataType.MONEY);
    Column isDeductPrice = of(DataType.FALSE);
    Column searchText = of(DataType.LONG_TEXT);
}
/*
From findAllBussSchedulePoint
--> BussSchedule.
pointFrom
pointTo
date

--> Trip
All tripUsers -> PathPoint: iTo < pointFrom || iFrom > pointTo.order

Buss: disabledSeats

TripUser
trip, user, pointFrom, pointTo, seats, totalSeats



 */
