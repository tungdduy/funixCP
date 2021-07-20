package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;

import static data.entities.abstracts.DataType.DESCRIPTION;
import static data.entities.abstracts.DataType.FALSE;

@SuppressWarnings("all")
public class Company extends AbstractEntity {
    Column companyDesc = of(DESCRIPTION);
    Column companyName = of(DESCRIPTION);
    Column isLock = of(FALSE);


    MapColumn employees = map(Employee.class);
}
