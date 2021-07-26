package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.entity.BussEmployee;
import net.timxekhach.operation.data.mapped.BussEmployee_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussEmployeeRepository extends JpaRepository<BussEmployee, BussEmployee_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateBussEmployee(Map<String, String> data) {
        BussEmployee bussEmployee = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(BussEmployee.pk(data)));
        bussEmployee.setFieldByName(data);
        this.save(bussEmployee);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
