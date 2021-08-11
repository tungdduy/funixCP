package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.BussSchedulePoint;
import net.timxekhach.operation.data.mapped.BussSchedulePoint_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussSchedulePointRepository extends JpaRepository<BussSchedulePoint, BussSchedulePoint_MAPPED.Pk> {

    void deleteByBussSchedulePointId(Long id);
    void deleteAllByBussSchedulePointIdIn(List<Long> ids);
    BussSchedulePoint findByBussSchedulePointId(Long id);
    @SuppressWarnings("unused")
    void deleteByBussPointId(Long bussPointId);
    @SuppressWarnings("unused")
    List<BussSchedulePoint> findByBussPointId(Long bussPointId);
    @SuppressWarnings("unused")
    void deleteByBussPointIdAndBussScheduleId(Long bussPointId, Long bussScheduleId);
    @SuppressWarnings("unused")
    List<BussSchedulePoint> findByBussPointIdAndBussScheduleId(Long bussPointId, Long bussScheduleId);
    @SuppressWarnings("unused")
    void deleteByBussScheduleId(Long bussScheduleId);
    @SuppressWarnings("unused")
    List<BussSchedulePoint> findByBussScheduleId(Long bussScheduleId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
