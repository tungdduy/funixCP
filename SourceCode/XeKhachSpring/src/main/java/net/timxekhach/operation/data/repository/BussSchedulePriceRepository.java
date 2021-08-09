package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.BussSchedulePrice;
import net.timxekhach.operation.data.mapped.BussSchedulePrice_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface BussSchedulePriceRepository extends JpaRepository<BussSchedulePrice, BussSchedulePrice_MAPPED.Pk> {

    void deleteByBussSchedulePriceId(Long id);
    void deleteAllByBussSchedulePriceIdIn(List<Long> ids);
    BussSchedulePrice findByBussSchedulePriceId(Long id);
    @SuppressWarnings("unused")
    void deleteByBussScheduleId(Long bussScheduleId);
    @SuppressWarnings("unused")
    List<BussSchedulePrice> findByBussScheduleId(Long bussScheduleId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
