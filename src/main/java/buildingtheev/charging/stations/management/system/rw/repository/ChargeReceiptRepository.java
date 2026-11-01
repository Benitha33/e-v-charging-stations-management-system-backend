package buildingtheev.charging.stations.management.system.rw.repository;

import buildingtheev.charging.stations.management.system.rw.entity.ChargeReceipt;
import buildingtheev.charging.stations.management.system.rw.entity.ChargingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface ChargeReceiptRepository extends JpaRepository<ChargeReceipt, UUID> {
    boolean existsByChargingSession(ChargingSession cs);
    @Query("SELECT r FROM ChargeReceipt r WHERE r.chargingSession.driver.plateNumber = :p ORDER BY r.issuedDate DESC")
    List<ChargeReceipt> findByDriverPlate(@Param("p") String p);
}
