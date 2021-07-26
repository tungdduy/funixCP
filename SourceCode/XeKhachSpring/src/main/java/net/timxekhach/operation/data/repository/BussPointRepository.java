package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import net.timxekhach.operation.data.mapped.BussPoint_MAPPED;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.BussPoint;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussPointRepository extends JpaRepository<BussPoint, BussPoint_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateBussPoint(Map<String, String> data) {
        BussPoint bussPoint = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(BussPoint.pk(data)));
        bussPoint.setFieldByName(data);
        this.save(bussPoint);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
