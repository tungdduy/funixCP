package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;

import static data.entities.abstracts.DataType.DESCRIPTION;

public class SeatType extends AbstractEntity {
    {
        pk(BussType.class);
    }
    Column name = of(DESCRIPTION);
}
