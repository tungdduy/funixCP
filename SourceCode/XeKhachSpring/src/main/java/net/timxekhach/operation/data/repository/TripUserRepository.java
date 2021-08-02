package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import net.timxekhach.operation.data.entity.TripUser;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.TripUser_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripUserRepository extends JpaRepository<TripUser, TripUser_MAPPED.Pk> {

    @Modifying
    @Query("delete from TripUser e where e.tripUserId in ?1")
    void deleteByTripUserId(Long... id);
    TripUser findByTripUserId(Long id);

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
