package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import net.timxekhach.operation.data.entity.PathPoint;
import net.timxekhach.operation.data.mapped.PathPoint_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface PathPointRepository extends JpaRepository<PathPoint, PathPoint_MAPPED.Pk> {

    void deleteByPathPointId(Long id);
    void deleteAllByPathPointIdIn(List<Long> ids);
    PathPoint findByPathPointId(Long id);
    List<PathPoint> findByPathPointIdIn(List<Long> id);
    Integer countPathPointIdByPathId(Long path);
    @SuppressWarnings("unused")
    void deleteByLocationId(Long locationId);
    @SuppressWarnings("unused")
    List<PathPoint> findByLocationIdOrderByPointOrderAsc(Long locationId);
    @SuppressWarnings("unused")
    void deleteByLocationIdAndPathId(Long locationId, Long pathId);
    @SuppressWarnings("unused")
    List<PathPoint> findByLocationIdAndPathIdOrderByPointOrderAsc(Long locationId, Long pathId);
    @SuppressWarnings("unused")
    void deleteByPathId(Long pathId);
    @SuppressWarnings("unused")
    List<PathPoint> findByPathIdOrderByPointOrderAsc(Long pathId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
