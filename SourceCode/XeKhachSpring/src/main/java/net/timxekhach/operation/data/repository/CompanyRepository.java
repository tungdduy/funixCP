package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.mapped.Company_MAPPED;
import net.timxekhach.operation.response.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface CompanyRepository extends JpaRepository<Company, Company_MAPPED.Pk> {
    @SuppressWarnings("unused")
    default void updateCompany(Map<String, String> data) {
        Company company = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(this.findById(Company.pk(data)));
        company.setFieldByName(data);
        this.save(company);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
