package buildingtheev.charging.stations.management.system.rw.repository;

import buildingtheev.charging.stations.management.system.rw.entity.Bay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BayRepository extends JpaRepository<Bay, UUID> {
    Optional<Bay> findByBayNumber(int bayNumber);
    @Query("SELECT b FROM Bay b WHERE NOT EXISTS (SELECT p FROM ChargingPoint p WHERE p.bay = b) ORDER BY b.bayNumber")
    List<Bay> findFreeBays();
}
