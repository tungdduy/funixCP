package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.BussType;
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.BussType_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussTypeRepository extends JpaRepository<BussType, BussType_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateBussType(Map<String, String> data) {
        BussType bussType = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(BussType.pk(data)));
        bussType.setFieldByName(data);
        this.save(bussType);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
