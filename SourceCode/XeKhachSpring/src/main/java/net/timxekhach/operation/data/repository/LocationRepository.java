package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.Location_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.Location;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface LocationRepository extends JpaRepository<Location, Location_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateLocation(Map<String, String> data) {
        Location location = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(Location.pk(data)));
        location.setFieldByName(data);
        this.save(location);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
