package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import data.models.Pk;

@SuppressWarnings("all")
public class Company extends AbstractEntity {
    Pk companyId = pk();
    Column companyDesc = of(String.class);
    MapColumn employees = map(new Employee().company);
}
