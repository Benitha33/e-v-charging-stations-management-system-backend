package buildingtheev.charging.stations.management.system.rw.service;

import buildingtheev.charging.stations.management.system.rw.entity.SessionStatus;
import buildingtheev.charging.stations.management.system.rw.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {
    private final DriverRepository driverRepo;
    private final ChargingPointRepository cpRepo;
    private final BayRepository bayRepo;
    private final ChargingSessionRepository sessionRepo;
    private final ChargeReceiptRepository receiptRepo;
    public DashboardService(DriverRepository d, ChargingPointRepository c, BayRepository b,
                            ChargingSessionRepository s, ChargeReceiptRepository r) {
        this.driverRepo = d; this.cpRepo = c; this.bayRepo = b; this.sessionRepo = s; this.receiptRepo = r;
    }
    public Map<String, Long> summary() {
        Map<String, Long> m = new LinkedHashMap<>();
        m.put("drivers", driverRepo.count());
        m.put("chargingPoints", cpRepo.count());
        m.put("freeBays", (long) bayRepo.findFreeBays().size());
        m.put("sessionsToday", sessionRepo.countBySessionDate(LocalDate.now()));
        m.put("reserved", sessionRepo.countByStatus(SessionStatus.RESERVED));
        m.put("chargeReceipts", receiptRepo.count());
        return m;
    }
}
