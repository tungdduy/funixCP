package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.Location;
import net.timxekhach.operation.data.mapped.Location_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface LocationRepository extends JpaRepository<Location, Location_MAPPED.Pk> {

    void deleteByLocationId(Long id);
    void deleteAllByLocationIdIn(List<Long> ids);
    Location findByLocationId(Long id);
    List<Location> findByLocationIdIn(List<Long> id);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    List<Location> findTop20ByLocationNameContainsOrderByLocationIdDesc(String searchString);
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
