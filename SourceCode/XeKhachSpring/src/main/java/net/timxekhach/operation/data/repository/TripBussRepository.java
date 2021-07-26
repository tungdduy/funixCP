package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.entity.TripBuss;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.mapped.TripBuss_MAPPED;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripBussRepository extends JpaRepository<TripBuss, TripBuss_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateTripBuss(Map<String, String> data) {
        TripBuss tripBuss = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(TripBuss.pk(data)));
        tripBuss.setFieldByName(data);
        this.save(tripBuss);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
