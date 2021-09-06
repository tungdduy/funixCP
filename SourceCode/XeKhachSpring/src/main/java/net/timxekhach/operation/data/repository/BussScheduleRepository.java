package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.operation.data.mapped.BussSchedule_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussScheduleRepository extends JpaRepository<BussSchedule, BussSchedule_MAPPED.Pk> {

    void deleteByBussScheduleId(Long id);
    void deleteAllByBussScheduleIdIn(List<Long> ids);
    BussSchedule findByBussScheduleId(Long id);
    List<BussSchedule> findByBussScheduleIdIn(List<Long> id);
    Integer countBussScheduleIdByCompanyId(Long company);
    Integer countBussScheduleIdByBussId(Long buss);
    @SuppressWarnings("unused")
    void deleteByBussId(Long bussId);
    @SuppressWarnings("unused")
    List<BussSchedule> findByBussId(Long bussId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    List<BussSchedule> findByEffectiveDateFromLessThanEqualAndWorkingDaysContains(Date date, String dayInWeekExpression);

    List<BussSchedule> findByCompanyIdOrderByLaunchTimeDesc(Long companyId);

    Long countBussScheduleIdByPathPathId(Long pathId);
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
