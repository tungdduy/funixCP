package net.timxekhach.operation.data.repository;

import net.timxekhach.operation.data.entity.Buss;
import net.timxekhach.operation.data.entity.User;
import net.timxekhach.operation.data.mapped.Buss_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BussRepository extends JpaRepository<Buss, Buss_MAPPED.Pk> {

}