package buildingtheev.charging.stations.management.system.rw.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bay")
public class Bay {
    @Id @GeneratedValue
    private UUID id;
    private int bayNumber;
    private String name;

    public Bay() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public int getBayNumber() { return bayNumber; }
    public void setBayNumber(int n) { this.bayNumber = n; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
