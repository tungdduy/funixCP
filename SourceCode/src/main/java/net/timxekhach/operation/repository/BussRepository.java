package net.timxekhach.operation.repository;

import net.timxekhach.operation.entity.Buss;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BussRepository extends JpaRepository<Buss, Long> {
}