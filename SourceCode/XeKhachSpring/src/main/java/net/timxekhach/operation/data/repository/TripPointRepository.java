package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.TripPoint_MAPPED;
import net.timxekhach.operation.data.entity.TripPoint;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripPointRepository extends JpaRepository<TripPoint, TripPoint_MAPPED.Pk> {

    void deleteByTripPointId(Long id);
    void deleteAllByTripPointIdIn(List<Long> ids);
    TripPoint findByTripPointId(Long id);
    Integer countTripPointIdByBussTripId(Long bussTripId);
    @SuppressWarnings("unused")
    void deleteByBussTripId(Long bussTripId);
    @SuppressWarnings("unused")
    List<TripPoint> findByBussTripId(Long bussTripId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
