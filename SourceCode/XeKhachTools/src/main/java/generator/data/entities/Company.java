package generator.data.entities;

import generator.data.entities.abstracts.AbstractEntityBuilder;

import java.util.List;

@SuppressWarnings("all")
public class Company extends AbstractEntityBuilder {
    class Pk {
        String companyId;
    }
    String companyDesc;
    List<Employee> allEmployees;


}
