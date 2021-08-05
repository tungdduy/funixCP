package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import net.timxekhach.operation.data.entity.Caller;
import net.timxekhach.operation.data.mapped.Caller_MAPPED;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface CallerRepository extends JpaRepository<Caller, Caller_MAPPED.Pk> {

    void deleteByCallerId(Long id);
    void deleteAllByCallerIdIn(List<Long> ids);
    Caller findByCallerId(Long id);
    Integer countCallerIdByCompanyId(Long company);
    @SuppressWarnings("unused")
    void deleteByCompanyId(Long companyId);
    @SuppressWarnings("unused")
    List<Caller> findByCompanyId(Long companyId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
