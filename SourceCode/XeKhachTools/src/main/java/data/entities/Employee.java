package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Employee extends AbstractEntity {
   {pk(Company.class);}
   // OneToOne
   MapColumn user = map(User.class).unique();
   // OneToMany
   MapColumn seats = map(EmployeeSeat.class);
   // ManyToOne
   MapColumn city = map(City.class);
}
