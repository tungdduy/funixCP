package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import net.timxekhach.operation.data.entity.Company;
import net.timxekhach.operation.data.mapped.Company_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface CompanyRepository extends JpaRepository<Company, Company_MAPPED.Pk> {

    void deleteByCompanyId(Long id);
    void deleteAllByCompanyIdIn(List<Long> ids);
    Company findByCompanyId(Long id);
    List<Company> findByCompanyIdIn(List<Long> id);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
