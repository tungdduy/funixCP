package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.BussTrip_MAPPED;
import net.timxekhach.operation.data.entity.BussTrip;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussTripRepository extends JpaRepository<BussTrip, BussTrip_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateBussTrip(Map<String, String> data) {
        BussTrip bussTrip = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(BussTrip.pk(data)));
        bussTrip.setFieldByName(data);
        this.save(bussTrip);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
