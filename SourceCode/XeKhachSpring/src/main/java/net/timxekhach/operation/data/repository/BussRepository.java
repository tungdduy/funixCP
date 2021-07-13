package net.timxekhach.operation.data.repository;

import net.timxekhach.operation.data.entity.Buss;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BussRepository extends JpaRepository<Buss, Long> {
}