package generator.data.entities;

import generator.data.entities.abstracts.AbstractEntity;
import generator.data.models.Column;
import generator.data.models.Pk;

import java.util.List;

@SuppressWarnings("all")
public class Company extends AbstractEntity {
    Pk companyId = pk(String.class);
    Column employees = of(Employee.class).list();
}
