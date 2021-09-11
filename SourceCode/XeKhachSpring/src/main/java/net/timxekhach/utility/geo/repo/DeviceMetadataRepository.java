package net.timxekhach.utility.geo.repo;

import net.timxekhach.utility.geo.model.DeviceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {
  List<DeviceMetadata> findByUsername(String username);
  int countByUsername(String username);
}
