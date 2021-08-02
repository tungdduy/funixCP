package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.Employee_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.Employee;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Employee_MAPPED.Pk> {

    @Modifying
    @Query("delete from Employee e where e.employeeId in ?1")
    void deleteByEmployeeId(Long... id);
    Employee findByEmployeeId(Long id);

    @SuppressWarnings("unused")
    void deleteByCompanyId(Long companyId);
    @SuppressWarnings("unused")
    List<Employee> findByCompanyId(Long companyId);
    @SuppressWarnings("unused")
    void deleteByCompanyIdAndUserId(Long companyId, Long userId);
    @SuppressWarnings("unused")
    List<Employee> findByCompanyIdAndUserId(Long companyId, Long userId);
    @SuppressWarnings("unused")
    void deleteByUserId(Long userId);
    @SuppressWarnings("unused")
    List<Employee> findByUserId(Long userId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
