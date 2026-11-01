package buildingtheev.charging.stations.management.system.rw.repository;

import buildingtheev.charging.stations.management.system.rw.entity.ConnectorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface ConnectorTypeRepository extends JpaRepository<ConnectorType, UUID> {
    @Query("SELECT c FROM ConnectorType c WHERE c.chargingPoints IS EMPTY")
    List<ConnectorType> findUnused();
}
