package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.mapped.Trip_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripRepository extends JpaRepository<Trip, Trip_MAPPED.Pk> {

// ____________________ ::BODY_SEPARATOR:: ____________________ //

    @Query("select Trip from Trip t where t.startPoint.bussPointId = ?1 AND t.endPoint.bussPointId = ?2")
    List<Trip> findByStartPointAndEndPoint(long startPoint, long endPoint);

    @Query("select Trip from Trip t where t.startPoint.bussPointId = ?1 AND t.endPoint.bussPointId = ?2 " +
            "AND t.status = net.timxekhach.operation.data.enumeration.TripStatus.PENDING")
    List<Trip> findByStartPointAndEndPointAndStatus(long startPoint, long endPoint);

    Trip findTripByTripId(Long tripId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
