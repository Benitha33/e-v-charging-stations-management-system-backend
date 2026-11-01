package buildingtheev.charging.stations.management.system.rw.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "charging_point")
public class ChargingPoint {
    @Id @GeneratedValue
    private UUID id;
    private String code;
    private String brand;
    private int powerKw;

    @ManyToOne @JoinColumn(name = "bay_id")
    private Bay bay;

    @ManyToMany
    @JoinTable(name = "charging_point_connector_type",
            joinColumns = @JoinColumn(name = "charging_point_id"),
            inverseJoinColumns = @JoinColumn(name = "connector_type_id"))
    private List<ConnectorType> connectorTypes = new ArrayList<>();

    public ChargingPoint() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String c) { this.code = c; }
    public String getBrand() { return brand; }
    public void setBrand(String b) { this.brand = b; }
    public int getPowerKw() { return powerKw; }
    public void setPowerKw(int p) { this.powerKw = p; }
    public Bay getBay() { return bay; }
    public void setBay(Bay b) { this.bay = b; }
    public List<ConnectorType> getConnectorTypes() { return connectorTypes; }
    public void setConnectorTypes(List<ConnectorType> c) { this.connectorTypes = c; }
}
