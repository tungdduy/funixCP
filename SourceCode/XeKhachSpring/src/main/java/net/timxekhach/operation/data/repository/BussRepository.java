package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import net.timxekhach.operation.data.entity.Buss;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.Buss_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussRepository extends JpaRepository<Buss, Buss_MAPPED.Pk> {

    void deleteByBussId(Long id);
    void deleteAllByBussIdIn(List<Long> ids);
    Buss findByBussId(Long id);
    Integer countBussIdByCompanyId(Long company);
    @SuppressWarnings("unused")
    void deleteByBussTypeId(Long bussTypeId);
    @SuppressWarnings("unused")
    List<Buss> findByBussTypeId(Long bussTypeId);
    @SuppressWarnings("unused")
    void deleteByBussTypeIdAndCompanyId(Long bussTypeId, Long companyId);
    @SuppressWarnings("unused")
    List<Buss> findByBussTypeIdAndCompanyId(Long bussTypeId, Long companyId);
    @SuppressWarnings("unused")
    void deleteByCompanyId(Long companyId);
    @SuppressWarnings("unused")
    List<Buss> findByCompanyId(Long companyId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
