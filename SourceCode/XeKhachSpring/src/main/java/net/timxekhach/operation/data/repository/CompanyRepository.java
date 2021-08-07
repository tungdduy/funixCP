package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import java.util.List;
import net.timxekhach.operation.data.mapped.Company_MAPPED;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface CompanyRepository extends JpaRepository<Company, Company_MAPPED.Pk> {

    void deleteByCompanyId(Long id);
    void deleteAllByCompanyIdIn(List<Long> ids);
    Company findByCompanyId(Long id);

// ____________________ ::BODY_SEPARATOR:: ____________________ //



// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
