package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Employee extends AbstractEntity {
   {pk(Company.class);}
   MapColumn user = map(User.class).unique();
   MapColumn seats = map(EmployeeSeat.class);
   MapColumn city = map(City.class);
}
