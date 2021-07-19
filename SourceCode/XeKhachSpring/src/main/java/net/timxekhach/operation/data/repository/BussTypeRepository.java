package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.BussType;
import net.timxekhach.operation.data.mapped.BussType_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussTypeRepository extends JpaRepository<BussType, BussType_MAPPED.Pk> {

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
