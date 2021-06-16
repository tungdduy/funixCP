package net.timxekhach.operation.repository;

import net.timxekhach.operation.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}