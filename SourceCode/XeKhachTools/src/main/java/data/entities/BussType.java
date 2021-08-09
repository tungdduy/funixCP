package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.entities.abstracts.DataType;
import data.models.Column;
import data.models.CountMethod;
import util.constants.BussTypeEnum;

public class BussType extends AbstractEntity {
    Column bussTypeCode = of(DataType.CODE);
    Column bussTypeName = of(DataType.DESCRIPTION);
    Column bussTypeDesc = of(DataType.DESCRIPTION);

    Column totalSeats = of(DataType.QUANTITY);
    CountMethod totalBusses = count(Buss.class);

    @Override
    public boolean hasProfileImage() {
        return true;
    }
}
