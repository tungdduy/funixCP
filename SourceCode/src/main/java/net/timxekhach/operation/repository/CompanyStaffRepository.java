package net.timxekhach.operation.repository;

import net.timxekhach.operation.entity.CompanyStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyStaffRepository extends JpaRepository<CompanyStaff, Long> {
}