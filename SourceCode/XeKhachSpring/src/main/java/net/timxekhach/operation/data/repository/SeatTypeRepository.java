package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.SeatType_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.SeatType;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, SeatType_MAPPED.Pk> {

    void deleteBySeatTypeId(Long id);
    void deleteAllBySeatTypeIdIn(List<Long> ids);
    SeatType findBySeatTypeId(Long id);
    @SuppressWarnings("unused")
    void deleteByBussTypeId(Long bussTypeId);
    @SuppressWarnings("unused")
    List<SeatType> findByBussTypeId(Long bussTypeId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
