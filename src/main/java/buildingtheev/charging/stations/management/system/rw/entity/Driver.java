package buildingtheev.charging.stations.management.system.rw.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "driver")
public class Driver {
    @Id @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String plateNumber;
    private String email;
    private String phone;

    public Driver() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String n) { this.firstName = n; }
    public String getLastName() { return lastName; }
    public void setLastName(String n) { this.lastName = n; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String p) { this.plateNumber = p; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getPhone() { return phone; }
    public void setPhone(String p) { this.phone = p; }
}
