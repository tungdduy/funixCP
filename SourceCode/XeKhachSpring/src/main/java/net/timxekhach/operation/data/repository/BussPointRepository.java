package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import net.timxekhach.operation.data.mapped.BussPoint_MAPPED;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.BussPoint;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussPointRepository extends JpaRepository<BussPoint, BussPoint_MAPPED.Pk> {

    @Modifying
    @Query("delete from BussPoint e where e.bussPointId in ?1")
    void deleteByBussPointId(Long... id);
    BussPoint findByBussPointId(Long id);

    @SuppressWarnings("unused")
    void deleteByLocationId(Long locationId);
    @SuppressWarnings("unused")
    List<BussPoint> findByLocationId(Long locationId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
