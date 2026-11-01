package buildingtheev.charging.stations.management.system.rw.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "connector_type")
public class ConnectorType {
    @Id @GeneratedValue
    private UUID id;
    private String name;
    @ManyToMany(mappedBy = "connectorTypes") @JsonIgnore
    private List<ChargingPoint> chargingPoints = new ArrayList<>();

    public ConnectorType() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<ChargingPoint> getChargingPoints() { return chargingPoints; }
    public void setChargingPoints(List<ChargingPoint> c) { this.chargingPoints = c; }
}
