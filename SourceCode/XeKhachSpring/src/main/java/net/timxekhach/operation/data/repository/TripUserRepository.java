package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.TripUser;
import net.timxekhach.operation.data.mapped.TripUser_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripUserRepository extends JpaRepository<TripUser, TripUser_MAPPED.Pk> {

    void deleteByTripUserId(Long id);
    void deleteAllByTripUserIdIn(List<Long> ids);
    TripUser findByTripUserId(Long id);
    List<TripUser> findByTripUserIdIn(List<Long> id);
    Integer countTripUserIdByTripId(Long trip);
    @SuppressWarnings("unused")
    void deleteByTripId(Long tripId);
    @SuppressWarnings("unused")
    List<TripUser> findByTripId(Long tripId);
    @SuppressWarnings("unused")
    void deleteByTripIdAndUserId(Long tripId, Long userId);
    @SuppressWarnings("unused")
    List<TripUser> findByTripIdAndUserId(Long tripId, Long userId);
    @SuppressWarnings("unused")
    void deleteByUserId(Long userId);
    @SuppressWarnings("unused")
    List<TripUser> findByUserId(Long userId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
