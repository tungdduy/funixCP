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
    List<BussSchedulePoint> findByBussSchedulePointIdIn(List<Long> id);
    Integer countBussSchedulePointIdByPathPointId(Long pathPoint);
    Integer countBussSchedulePointIdByBussScheduleId(Long bussSchedule);
    @SuppressWarnings("unused")
    void deleteByBussScheduleId(Long bussScheduleId);
    @SuppressWarnings("unused")
    List<BussSchedulePoint> findByBussScheduleId(Long bussScheduleId);
    @SuppressWarnings("unused")
    void deleteByBussScheduleIdAndPathPointId(Long bussScheduleId, Long pathPointId);
    @SuppressWarnings("unused")
    List<BussSchedulePoint> findByBussScheduleIdAndPathPointId(Long bussScheduleId, Long pathPointId);
    @SuppressWarnings("unused")
    void deleteByPathPointId(Long pathPointId);
    @SuppressWarnings("unused")
    List<BussSchedulePoint> findByPathPointId(Long pathPointId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    List<BussSchedulePoint> findTop20BySearchTextContains(String searchText);
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
