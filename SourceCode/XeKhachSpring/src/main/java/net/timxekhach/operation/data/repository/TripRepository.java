package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.Trip;
import net.timxekhach.operation.data.mapped.Trip_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Date;
import java.util.Optional;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripRepository extends JpaRepository<Trip, Trip_MAPPED.Pk> {

    void deleteByTripId(Long id);
    void deleteAllByTripIdIn(List<Long> ids);
    Trip findByTripId(Long id);
    List<Trip> findByTripIdIn(List<Long> id);
    Integer countTripIdByCompanyId(Long company);
    @SuppressWarnings("unused")
    void deleteByBussScheduleId(Long bussScheduleId);
    @SuppressWarnings("unused")
    List<Trip> findByBussScheduleId(Long bussScheduleId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    Optional<Trip> findFirstByBussScheduleIdAndLaunchDate(Long bussScheduleId, Date launchDate);
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
