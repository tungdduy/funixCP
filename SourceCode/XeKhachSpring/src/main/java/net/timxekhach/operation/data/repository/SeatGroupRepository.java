package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import net.timxekhach.operation.data.entity.SeatGroup;
import net.timxekhach.operation.data.mapped.SeatGroup_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface SeatGroupRepository extends JpaRepository<SeatGroup, SeatGroup_MAPPED.Pk> {

    void deleteBySeatGroupId(Long id);
    void deleteAllBySeatGroupIdIn(List<Long> ids);
    SeatGroup findBySeatGroupId(Long id);
    List<SeatGroup> findBySeatGroupIdIn(List<Long> id);
    @SuppressWarnings("unused")
    void deleteByBussTypeId(Long bussTypeId);
    @SuppressWarnings("unused")
    List<SeatGroup> findByBussTypeId(Long bussTypeId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
