package data.entities;

import data.entities.abstracts.AbstractEntity;
import static data.entities.abstracts.DataType.*;
import data.models.Column;
import data.models.MapColumn;
import lombok.Getter;

@Getter
public class Buss extends AbstractEntity {
    {pk(Company.class);}
    Column bussDesc = of(DESCRIPTION);
    MapColumn seats = map(Seat.class);
}
