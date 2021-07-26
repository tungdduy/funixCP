package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.Employee_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.Employee;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Employee_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateEmployee(Map<String, String> data) {
        Employee employee = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(Employee.pk(data)));
        employee.setFieldByName(data);
        this.save(employee);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
