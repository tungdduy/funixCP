package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.SeatType_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.SeatType;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, SeatType_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateSeatType(Map<String, String> data) {
        SeatType seatType = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(SeatType.pk(data)));
        seatType.setFieldByName(data);
        this.save(seatType);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
