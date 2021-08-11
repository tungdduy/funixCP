package data.entities;

import data.entities.abstracts.AbstractEntity;
import data.models.Column;
import data.models.CountMethod;
import data.models.MapColumn;
import lombok.Getter;
import lombok.Setter;

import static data.entities.abstracts.DataType.FALSE;

public class Employee extends AbstractEntity {
   {
      pk(Company.class);
      pk(User.class);
   }
   CountMethod countBusses = count(BussEmployee.class);
   Column isLock = of(FALSE);
}
