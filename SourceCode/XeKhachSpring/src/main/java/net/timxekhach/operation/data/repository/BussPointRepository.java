package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.mapped.BussPoint_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussPointRepository extends JpaRepository<BussPoint, BussPoint_MAPPED.Pk> {

// ____________________ ::BODY_SEPARATOR:: ____________________ //

    List<BussPoint> findByBussPointDescContains(String description);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
