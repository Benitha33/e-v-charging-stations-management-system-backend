package buildingtheev.charging.stations.management.system.rw.repository;

import buildingtheev.charging.stations.management.system.rw.entity.Bay;
import buildingtheev.charging.stations.management.system.rw.entity.ChargingPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface ChargingPointRepository extends JpaRepository<ChargingPoint, UUID> {
    @Query("SELECT DISTINCT p FROM ChargingPoint p JOIN p.connectorTypes c WHERE LOWER(c.name) = LOWER(:name)")
    List<ChargingPoint> findByConnectorTypeName(@Param("name") String name);
    boolean existsByBay(Bay bay);
}
