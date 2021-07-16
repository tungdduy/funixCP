package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;

import static data.entities.abstracts.DataType.*;

@SuppressWarnings("all")
public class Company extends AbstractEntity {
    Column companyDesc = of(DESCRIPTION);
    MapColumn employees = map(Employee.class);
}
