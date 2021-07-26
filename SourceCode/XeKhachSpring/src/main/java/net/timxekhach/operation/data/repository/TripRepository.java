package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import net.timxekhach.operation.data.entity.Trip;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.Trip_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripRepository extends JpaRepository<Trip, Trip_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateTrip(Map<String, String> data) {
        Trip trip = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(Trip.pk(data)));
        trip.setFieldByName(data);
        this.save(trip);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
