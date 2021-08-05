package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.BussTrip_MAPPED;
import net.timxekhach.operation.data.entity.BussTrip;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussTripRepository extends JpaRepository<BussTrip, BussTrip_MAPPED.Pk> {

    void deleteByBussTripId(Long id);
    void deleteAllByBussTripIdIn(List<Long> ids);
    BussTrip findByBussTripId(Long id);
    Integer countBussTripIdByCompanyId(Long company);
    @SuppressWarnings("unused")
    void deleteByBussId(Long bussId);
    @SuppressWarnings("unused")
    List<BussTrip> findByBussId(Long bussId);
    @SuppressWarnings("unused")
    void deleteByBussIdAndCompanyId(Long bussId, Long companyId);
    @SuppressWarnings("unused")
    List<BussTrip> findByBussIdAndCompanyId(Long bussId, Long companyId);
    @SuppressWarnings("unused")
    void deleteByCompanyId(Long companyId);
    @SuppressWarnings("unused")
    List<BussTrip> findByCompanyId(Long companyId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
