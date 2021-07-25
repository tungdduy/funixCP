package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.SeatType;
import net.timxekhach.operation.data.entity.TripUserSeat;
import net.timxekhach.operation.data.mapped.TripUserSeat_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripUserSeatRepository extends JpaRepository<TripUserSeat, TripUserSeat_MAPPED.Pk> {

// ____________________ ::BODY_SEPARATOR:: ____________________ //

    List<SeatType> findAllByTripId(Long tripId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
