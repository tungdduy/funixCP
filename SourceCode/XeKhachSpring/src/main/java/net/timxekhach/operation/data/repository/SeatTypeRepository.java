package net.timxekhach.operation.data.repository;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.SeatType;
import net.timxekhach.operation.data.mapped.SeatType_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, SeatType_MAPPED.Pk> {

// ____________________ ::BODY_SEPARATOR:: ____________________ //

    List<SeatType> findAllByBussTypeId(Long bussTypeId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
