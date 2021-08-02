package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import net.timxekhach.operation.data.entity.BussType;
import org.springframework.stereotype.Repository;
import net.timxekhach.operation.data.mapped.BussType_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussTypeRepository extends JpaRepository<BussType, BussType_MAPPED.Pk> {

    @Modifying
    @Query("delete from BussType e where e.bussTypeId in ?1")
    void deleteByBussTypeId(Long... id);
    BussType findByBussTypeId(Long id);


// ____________________ ::BODY_SEPARATOR:: ____________________ //


// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
