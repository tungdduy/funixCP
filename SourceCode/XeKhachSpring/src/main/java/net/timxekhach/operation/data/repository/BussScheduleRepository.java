package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
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
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
