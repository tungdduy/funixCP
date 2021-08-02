package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import net.timxekhach.operation.data.mapped.TripUserSeat_MAPPED;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.entity.TripUserSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripUserSeatRepository extends JpaRepository<TripUserSeat, TripUserSeat_MAPPED.Pk> {

    @Modifying
    @Query("delete from TripUserSeat e where e.tripUserSeatId in ?1")
    void deleteByTripUserSeatId(Long... id);
    TripUserSeat findByTripUserSeatId(Long id);

    @SuppressWarnings("unused")
    void deleteBySeatTypeId(Long seatTypeId);
    @SuppressWarnings("unused")
    List<TripUserSeat> findBySeatTypeId(Long seatTypeId);
    @SuppressWarnings("unused")
    void deleteBySeatTypeIdAndTripId(Long seatTypeId, Long tripId);
    @SuppressWarnings("unused")
    List<TripUserSeat> findBySeatTypeIdAndTripId(Long seatTypeId, Long tripId);
    @SuppressWarnings("unused")
    void deleteBySeatTypeIdAndTripIdAndUserId(Long seatTypeId, Long tripId, Long userId);
    @SuppressWarnings("unused")
    List<TripUserSeat> findBySeatTypeIdAndTripIdAndUserId(Long seatTypeId, Long tripId, Long userId);
    @SuppressWarnings("unused")
    void deleteByTripId(Long tripId);
    @SuppressWarnings("unused")
    List<TripUserSeat> findByTripId(Long tripId);
    @SuppressWarnings("unused")
    void deleteByTripIdAndUserId(Long tripId, Long userId);
    @SuppressWarnings("unused")
    List<TripUserSeat> findByTripIdAndUserId(Long tripId, Long userId);
    @SuppressWarnings("unused")
    void deleteByUserId(Long userId);
    @SuppressWarnings("unused")
    List<TripUserSeat> findByUserId(Long userId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
