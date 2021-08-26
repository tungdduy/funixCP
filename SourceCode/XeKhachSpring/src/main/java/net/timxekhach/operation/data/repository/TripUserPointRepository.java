package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.TripUserPoint;
import net.timxekhach.operation.data.mapped.TripUserPoint_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripUserPointRepository extends JpaRepository<TripUserPoint, TripUserPoint_MAPPED.Pk> {

    void deleteByTripUserPointId(Long id);
    void deleteAllByTripUserPointIdIn(List<Long> ids);
    TripUserPoint findByTripUserPointId(Long id);
    List<TripUserPoint> findByTripUserPointIdIn(List<Long> id);
    @SuppressWarnings("unused")
    void deleteByPathPointId(Long pathPointId);
    @SuppressWarnings("unused")
    List<TripUserPoint> findByPathPointId(Long pathPointId);
    @SuppressWarnings("unused")
    void deleteByPathPointIdAndTripUserId(Long pathPointId, Long tripUserId);
    @SuppressWarnings("unused")
    List<TripUserPoint> findByPathPointIdAndTripUserId(Long pathPointId, Long tripUserId);
    @SuppressWarnings("unused")
    void deleteByTripUserId(Long tripUserId);
    @SuppressWarnings("unused")
    List<TripUserPoint> findByTripUserId(Long tripUserId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
