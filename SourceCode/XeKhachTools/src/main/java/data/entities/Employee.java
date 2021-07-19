package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;

import static data.entities.abstracts.DataType.FALSE;

@Getter @Setter
public class Employee extends AbstractEntity {
   {
      pk(Company.class);
   }
   Column isLock = of(FALSE);
   MapColumn user = map(User.class).unique();
}
