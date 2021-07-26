package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import net.timxekhach.operation.data.mapped.TripUserSeat_MAPPED;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.entity.TripUserSeat;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripUserSeatRepository extends JpaRepository<TripUserSeat, TripUserSeat_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateTripUserSeat(Map<String, String> data) {
        TripUserSeat tripUserSeat = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(TripUserSeat.pk(data)));
        tripUserSeat.setFieldByName(data);
        this.save(tripUserSeat);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
