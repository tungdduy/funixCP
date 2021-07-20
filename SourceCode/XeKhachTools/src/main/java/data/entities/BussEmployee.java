package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;

import static data.entities.abstracts.DataType.FALSE;

public class BussEmployee extends AbstractEntity {
    {
        pk(Buss.class);
        pk(Employee.class);
    }
    Column isLock = of(FALSE);
}
