package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.Employee_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.Employee;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Employee_MAPPED.Pk> {

    void deleteByEmployeeId(Long id);
    void deleteAllByEmployeeIdIn(List<Long> ids);
    Employee findByEmployeeId(Long id);
    Integer countEmployeeIdByCompanyId(Long companyId);
    Integer countEmployeeIdByUserId(Long userId);
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

    boolean existsByUserIdIn(List<Long> userIds);

    List<Employee> findByEmployeeIdIn(List<Long> userIds);

    List<Employee> findByUserIdIn(List<Long> userIds);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
