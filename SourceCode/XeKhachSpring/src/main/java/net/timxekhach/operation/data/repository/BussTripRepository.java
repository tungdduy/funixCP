package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.BussTrip_MAPPED;
import net.timxekhach.operation.data.entity.BussTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussTripRepository extends JpaRepository<BussTrip, BussTrip_MAPPED.Pk> {

    @Modifying
    @Query("delete from BussTrip e where e.bussTripId in ?1")
    void deleteByBussTripId(Long... id);
    BussTrip findByBussTripId(Long id);

    @SuppressWarnings("unused")
    void deleteByBussId(Long bussId);
    @SuppressWarnings("unused")
    List<BussTrip> findByBussId(Long bussId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
