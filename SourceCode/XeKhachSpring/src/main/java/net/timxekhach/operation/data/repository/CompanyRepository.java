package net.timxekhach.operation.data.repository;

import net.timxekhach.operation.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}