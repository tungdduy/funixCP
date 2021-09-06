package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import net.timxekhach.operation.data.entity.Employee;
import net.timxekhach.operation.data.mapped.Employee_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Employee_MAPPED.Pk> {

    void deleteByEmployeeId(Long id);
    void deleteAllByEmployeeIdIn(List<Long> ids);
    Employee findByEmployeeId(Long id);
    List<Employee> findByEmployeeIdIn(List<Long> id);
    Integer countEmployeeIdByCompanyId(Long company);
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
