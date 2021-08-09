package net.timxekhach.operation.data.repository;

// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import net.timxekhach.operation.data.entity.XeLocation;
import net.timxekhach.operation.data.mapped.XeLocation_MAPPED;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Repository
public interface XeLocationRepository extends JpaRepository<XeLocation, XeLocation_MAPPED.Pk> {

    void deleteByXeLocationId(Long id);
    void deleteAllByXeLocationIdIn(List<Long> ids);
    XeLocation findByXeLocationId(Long id);

// ____________________ ::BODY_SEPARATOR:: ____________________ //
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}
