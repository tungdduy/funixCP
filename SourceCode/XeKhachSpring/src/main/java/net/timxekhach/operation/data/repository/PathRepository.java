package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

import net.timxekhach.operation.data.entity.Path;
import net.timxekhach.operation.data.mapped.Path_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface PathRepository extends JpaRepository<Path, Path_MAPPED.Pk> {

    void deleteByPathId(Long id);
    void deleteAllByPathIdIn(List<Long> ids);
    Path findByPathId(Long id);
    List<Path> findByPathIdIn(List<Long> id);
    @SuppressWarnings("unused")
    void deleteByCompanyId(Long companyId);
    @SuppressWarnings("unused")
    List<Path> findByCompanyId(Long companyId);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
