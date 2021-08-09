package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.BussSchedule;
import net.timxekhach.operation.data.mapped.BussSchedule_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import net.timxekhach.operation.data.entity.BussPoint;
import java.util.Date;
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
    List<BussSchedule> findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndMondayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussSchedule> findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndTuesdayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussSchedule> findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndWednesdayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussSchedule> findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndThursdayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussSchedule> findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndFridayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussSchedule> findByStartPointAndEndPointAndEffectiveDateLessThanEqualFromAndSaturdayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);
    List<BussSchedule> findByStartPointAndEndPointAndEffectiveDateFromLessThanEqualAndSundayIsTrue(BussPoint startPoint, BussPoint endPoint, Date departureDate);

    @Query("SELECT s from BussSchedule s inner join BussPoint p " +
        "on (s.startPointBussPointId = p.bussPointId or s.endPointBussPointId = p.bussPointId)" +
        "where p.bussPointDesc like concat(?1, '%') and s.effectiveDateFrom <= current_date")
    List<BussSchedule> findBussScheduleByBussPointDesc(String description);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
