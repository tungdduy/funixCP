package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import net.timxekhach.operation.data.entity.Trip;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.Trip_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripRepository extends JpaRepository<Trip, Trip_MAPPED.Pk> {

    void deleteByTripId(Long id);
    void deleteAllByTripIdIn(List<Long> ids);
    Trip findByTripId(Long id);
    Integer countTripIdByCompanyId(Long company);
    @SuppressWarnings("unused")
    void deleteByBussId(Long bussId);
    @SuppressWarnings("unused")
    List<Trip> findByBussId(Long bussId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
