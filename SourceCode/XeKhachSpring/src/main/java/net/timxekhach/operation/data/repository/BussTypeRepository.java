package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import net.timxekhach.operation.data.entity.BussType;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.BussType_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussTypeRepository extends JpaRepository<BussType, BussType_MAPPED.Pk> {

    void deleteByBussTypeId(Long id);
    void deleteAllByBussTypeIdIn(List<Long> ids);
    BussType findByBussTypeId(Long id);

// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
