package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.TripUserSeat;
import net.timxekhach.operation.data.mapped.TripUserSeat_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripUserSeatRepository extends JpaRepository<TripUserSeat, TripUserSeat_MAPPED.Pk> {

    void deleteByTripUserSeatId(Long id);
    void deleteAllByTripUserSeatIdIn(List<Long> ids);
    TripUserSeat findByTripUserSeatId(Long id);
    List<TripUserSeat> findByTripUserSeatIdIn(List<Long> id);
    Integer countTripUserSeatIdByTripUserId(Long tripUser);
    @SuppressWarnings("unused")
    void deleteByTripUserId(Long tripUserId);
    @SuppressWarnings("unused")
    List<TripUserSeat> findByTripUserId(Long tripUserId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
