package generator.data.entities;

import generator.data.entities.abstracts.AbstractEntity;
import generator.data.models.Column;
import generator.data.models.Pk;

@SuppressWarnings("all")
public class Employee extends AbstractEntity {
   Pk employeeId = pk(String.class);
   Pk company = pk(Company.class);
   Column user = of(User.class);
}
