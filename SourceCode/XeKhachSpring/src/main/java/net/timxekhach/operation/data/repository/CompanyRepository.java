package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import net.timxekhach.operation.data.mapped.Company_MAPPED;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface CompanyRepository extends JpaRepository<Company, Company_MAPPED.Pk> {

    @Modifying
    @Query("delete from Company e where e.companyId in ?1")
    void deleteByCompanyId(Long... id);
    Company findByCompanyId(Long id);


// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
