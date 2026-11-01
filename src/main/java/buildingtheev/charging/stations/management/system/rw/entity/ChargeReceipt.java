package buildingtheev.charging.stations.management.system.rw.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "charge_receipt")
public class ChargeReceipt {
    @Id @GeneratedValue
    private UUID id;
    private String tariffName;
    private String remarks;
    private int energyKwh;
    private LocalDate issuedDate;

    @ManyToOne @JoinColumn(name = "charging_session_id")
    private ChargingSession chargingSession;

    public ChargeReceipt() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTariffName() { return tariffName; }
    public void setTariffName(String t) { this.tariffName = t; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String r) { this.remarks = r; }
    public int getEnergyKwh() { return energyKwh; }
    public void setEnergyKwh(int e) { this.energyKwh = e; }
    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate d) { this.issuedDate = d; }
    public ChargingSession getChargingSession() { return chargingSession; }
    public void setChargingSession(ChargingSession s) { this.chargingSession = s; }
}
