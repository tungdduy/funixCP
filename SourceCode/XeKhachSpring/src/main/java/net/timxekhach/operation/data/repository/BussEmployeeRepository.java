package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.entity.BussEmployee;
import net.timxekhach.operation.data.mapped.BussEmployee_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussEmployeeRepository extends JpaRepository<BussEmployee, BussEmployee_MAPPED.Pk> {

    void deleteByBussEmployeeId(Long id);
    void deleteAllByBussEmployeeIdIn(List<Long> ids);
    BussEmployee findByBussEmployeeId(Long id);
    Integer countBussEmployeeIdByBussId(Long bussId);
    Integer countBussEmployeeIdByEmployeeId(Long employeeId);
    @SuppressWarnings("unused")
    void deleteByBussId(Long bussId);
    @SuppressWarnings("unused")
    List<BussEmployee> findByBussId(Long bussId);
    @SuppressWarnings("unused")
    void deleteByBussIdAndEmployeeId(Long bussId, Long employeeId);
    @SuppressWarnings("unused")
    List<BussEmployee> findByBussIdAndEmployeeId(Long bussId, Long employeeId);
    @SuppressWarnings("unused")
    void deleteByEmployeeId(Long employeeId);
    @SuppressWarnings("unused")
    List<BussEmployee> findByEmployeeId(Long employeeId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
