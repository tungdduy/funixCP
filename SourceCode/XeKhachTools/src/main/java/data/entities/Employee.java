package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.MapColumn;
import data.models.Pk;

@SuppressWarnings("all")
public class Employee extends AbstractEntity {
   Pk employeeId = pk();
   Pk company = pk(Company.class);
   MapColumn user = map(new User().userId);
}
