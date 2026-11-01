package buildingtheev.charging.stations.management.system.rw.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "charging_session")
public class ChargingSession {
    @Id @GeneratedValue
    private UUID id;
    private LocalDate sessionDate;
    @Enumerated(EnumType.STRING)
    private SessionStatus status;
    private String vehicleModel;

    @ManyToOne @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne @JoinColumn(name = "charging_point_id")
    private ChargingPoint chargingPoint;

    public ChargingSession() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate d) { this.sessionDate = d; }
    public SessionStatus getStatus() { return status; }
    public void setStatus(SessionStatus s) { this.status = s; }
    public String getVehicleModel() { return vehicleModel; }
    public void setVehicleModel(String v) { this.vehicleModel = v; }
    public Driver getDriver() { return driver; }
    public void setDriver(Driver d) { this.driver = d; }
    public ChargingPoint getChargingPoint() { return chargingPoint; }
    public void setChargingPoint(ChargingPoint c) { this.chargingPoint = c; }
}
