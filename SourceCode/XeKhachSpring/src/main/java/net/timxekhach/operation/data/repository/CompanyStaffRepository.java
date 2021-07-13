package net.timxekhach.operation.data.repository;

import net.timxekhach.operation.data.entity.CompanyStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyStaffRepository extends JpaRepository<CompanyStaff, Long> {
}