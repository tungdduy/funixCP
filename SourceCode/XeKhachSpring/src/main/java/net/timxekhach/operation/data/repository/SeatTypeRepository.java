package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.SeatType_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import net.timxekhach.operation.data.entity.SeatType;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, SeatType_MAPPED.Pk> {

    @Modifying
    @Query("delete from SeatType e where e.seatTypeId in ?1")
    void deleteBySeatTypeId(Long... id);
    SeatType findBySeatTypeId(Long id);

    @SuppressWarnings("unused")
    void deleteByBussTypeId(Long bussTypeId);
    @SuppressWarnings("unused")
    List<SeatType> findByBussTypeId(Long bussTypeId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
