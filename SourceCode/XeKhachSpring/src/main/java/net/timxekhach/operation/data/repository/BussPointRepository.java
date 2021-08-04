package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import net.timxekhach.operation.data.mapped.BussPoint_MAPPED;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.BussPoint;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussPointRepository extends JpaRepository<BussPoint, BussPoint_MAPPED.Pk> {

    void deleteByBussPointId(Long id);
    void deleteAllByBussPointIdIn(List<Long> ids);
    BussPoint findByBussPointId(Long id);
    Integer countBussPointIdByLocationId(Long locationId);
    @SuppressWarnings("unused")
    void deleteByLocationId(Long locationId);
    @SuppressWarnings("unused")
    List<BussPoint> findByLocationId(Long locationId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
