package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.MapColumn;

public class TripPoint extends AbstractEntity {
    {
        pk(TripBuss.class);
    }
    MapColumn stopPoint = map(BussPoint.class);
}
