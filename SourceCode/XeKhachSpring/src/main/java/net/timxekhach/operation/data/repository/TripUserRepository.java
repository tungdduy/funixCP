package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import net.timxekhach.operation.data.entity.TripUser;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.TripUser_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface TripUserRepository extends JpaRepository<TripUser, TripUser_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateTripUser(Map<String, String> data) {
        TripUser tripUser = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(TripUser.pk(data)));
        tripUser.setFieldByName(data);
        this.save(tripUser);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
