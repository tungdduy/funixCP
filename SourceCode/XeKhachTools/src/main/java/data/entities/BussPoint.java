package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;

import static data.entities.abstracts.DataType.DESCRIPTION;

public class BussPoint extends AbstractEntity {
    {
        pk(Company.class);
        pk(XeLocation.class);
    }
    Column bussPointDesc = of(DESCRIPTION);

}
