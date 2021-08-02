package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.Location_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.Location;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface LocationRepository extends JpaRepository<Location, Location_MAPPED.Pk> {

    @Modifying
    @Query("delete from Location e where e.locationId in ?1")
    void deleteByLocationId(Long... id);
    Location findByLocationId(Long id);


// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
