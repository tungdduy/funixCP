package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import net.timxekhach.operation.data.entity.Trip;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.Trip_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripRepository extends JpaRepository<Trip, Trip_MAPPED.Pk> {

    @Modifying
    @Query("delete from Trip e where e.tripId in ?1")
    void deleteByTripId(Long... id);
    Trip findByTripId(Long id);

    @SuppressWarnings("unused")
    void deleteByBussId(Long bussId);
    @SuppressWarnings("unused")
    List<Trip> findByBussId(Long bussId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
