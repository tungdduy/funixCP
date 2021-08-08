package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;

import static data.entities.abstracts.DataType.FALSE;

public class Caller extends AbstractEntity {
    {
        pk(Company.class);
    }
    Column isLock = of(FALSE);
}
