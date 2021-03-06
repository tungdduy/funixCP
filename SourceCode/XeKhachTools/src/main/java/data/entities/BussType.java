package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;
import data.models.CountMethod;
import data.models.MapColumn;

public class BussType extends AbstractEntity {
    Column bussTypeName = of(DataType.DESCRIPTION);
    Column bussTypeDesc = of(DataType.DESCRIPTION);

    MapColumn seatGroups = map(SeatGroup.class).orderBy("seatGroupOrder DESC").renderJson();
    CountMethod totalBusses = count(Buss.class);

    @Override
    public boolean hasProfileImage() {
        return true;
    }
}
