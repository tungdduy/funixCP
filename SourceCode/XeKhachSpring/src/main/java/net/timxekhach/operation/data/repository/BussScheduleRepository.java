package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import java.util.Date;
import java.util.List;

import net.timxekhach.operation.data.entity.BussPoint;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.BussTrip_MAPPED;
import net.timxekhach.operation.data.entity.BussTrip;
import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.operation.data.mapped.BussSchedule_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussScheduleRepository extends JpaRepository<BussSchedule, BussSchedule_MAPPED.Pk> {

    void deleteByBussScheduleId(Long id);
    void deleteAllByBussScheduleIdIn(List<Long> ids);
    BussSchedule findByBussScheduleId(Long id);
    Integer countBussScheduleIdByCompanyId(Long company);
    @SuppressWarnings("unused")
    void deleteByBussId(Long bussId);
    @SuppressWarnings("unused")
    List<BussSchedule> findByBussId(Long bussId);
    @SuppressWarnings("unused")
    void deleteByBussIdAndCompanyId(Long bussId, Long companyId);
    @SuppressWarnings("unused")
    List<BussSchedule> findByBussIdAndCompanyId(Long bussId, Long companyId);
    @SuppressWarnings("unused")
    void deleteByCompanyId(Long companyId);
    @SuppressWarnings("unused")
    List<BussSchedule> findByCompanyId(Long companyId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

    List<BussTrip> findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndMondayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussTrip> findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndTuesdayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussTrip> findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndWednesdayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussTrip> findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndThursdayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussTrip> findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndFridayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussTrip> findByStartPointAndEndPointAndEffectiveDateGreaterThanEqualFromAndSaturdayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussTrip> findByStartPointAndEndPointAndEffectiveDateFromGreaterThanEqualAndSundayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
