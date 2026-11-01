package buildingtheev.charging.stations.management.system.rw.repository;

import buildingtheev.charging.stations.management.system.rw.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
    List<Driver> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
    @Query("SELECT DISTINCT s.driver FROM ChargingSession s WHERE s.chargingPoint.id = :id")
    List<Driver> findDriversOfChargingPoint(@Param("id") UUID id);
}
