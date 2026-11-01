package buildingtheev.charging.stations.management.system.rw.repository;

import buildingtheev.charging.stations.management.system.rw.entity.ChargingPoint;
import buildingtheev.charging.stations.management.system.rw.entity.ChargingSession;
import buildingtheev.charging.stations.management.system.rw.entity.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ChargingSessionRepository extends JpaRepository<ChargingSession, UUID> {
    List<ChargingSession> findByStatusOrderBySessionDateAsc(SessionStatus status);
    List<ChargingSession> findBySessionDateBetweenOrderBySessionDateAsc(LocalDate s, LocalDate e);
    boolean existsByChargingPointAndSessionDateAndStatusNot(ChargingPoint cp, LocalDate d, SessionStatus s);
    @Query("SELECT s.status, COUNT(s) FROM ChargingSession s GROUP BY s.status")
    List<Object[]> countByStatus();
    @Modifying
    @Query("UPDATE ChargingSession s SET s.status = buildingtheev.charging.stations.management.system.rw.entity.SessionStatus.CANCELLED WHERE s.chargingPoint.id = :id AND s.sessionDate = :d AND s.status <> buildingtheev.charging.stations.management.system.rw.entity.SessionStatus.FINISHED")
    int cancelDay(@Param("id") UUID id, @Param("d") LocalDate d);
    long countBySessionDate(LocalDate d);
    long countByStatus(SessionStatus s);
}
