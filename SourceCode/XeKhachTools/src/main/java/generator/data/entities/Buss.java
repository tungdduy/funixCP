package generator.data.entities;

import java.util.List;

@SuppressWarnings("all")
public class Buss {
    class Pk {
        String bussId;
    }
    String bussDesc;
    Company company;
    List<Employee> bussStaffs;
    List<Employee> bussStaffsOk;
    Employee callerStaff;


}
