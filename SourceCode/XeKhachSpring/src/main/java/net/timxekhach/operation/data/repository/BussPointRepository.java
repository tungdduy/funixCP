package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.mapped.BussPoint_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussPointRepository extends JpaRepository<BussPoint, BussPoint_MAPPED.Pk> {

    void deleteByBussPointId(Long id);
    void deleteAllByBussPointIdIn(List<Long> ids);
    BussPoint findByBussPointId(Long id);
    @SuppressWarnings("unused")
    void deleteByCompanyId(Long companyId);
    @SuppressWarnings("unused")
    List<BussPoint> findByCompanyId(Long companyId);
    @SuppressWarnings("unused")
    void deleteByCompanyIdAndXeLocationId(Long companyId, Long xeLocationId);
    @SuppressWarnings("unused")
    List<BussPoint> findByCompanyIdAndXeLocationId(Long companyId, Long xeLocationId);
    @SuppressWarnings("unused")
    void deleteByXeLocationId(Long xeLocationId);
    @SuppressWarnings("unused")
    List<BussPoint> findByXeLocationId(Long xeLocationId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    List<BussPoint> findAllByBussPointDescLike(String description);
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
