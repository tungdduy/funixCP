package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.MapColumn;

@SuppressWarnings("all")
public class Seat extends AbstractEntity {
    {
        pk(Buss.class);
    }
    MapColumn employees = map(EmployeeSeat.class);
}
