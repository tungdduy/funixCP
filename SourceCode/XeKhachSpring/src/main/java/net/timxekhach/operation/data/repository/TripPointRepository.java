package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.TripPoint_MAPPED;
import net.timxekhach.operation.data.entity.TripPoint;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripPointRepository extends JpaRepository<TripPoint, TripPoint_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateTripPoint(Map<String, String> data) {
        TripPoint tripPoint = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(TripPoint.pk(data)));
        tripPoint.setFieldByName(data);
        this.save(tripPoint);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
