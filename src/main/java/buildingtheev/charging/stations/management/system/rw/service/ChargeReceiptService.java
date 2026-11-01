package buildingtheev.charging.stations.management.system.rw.service;

import buildingtheev.charging.stations.management.system.rw.entity.ChargeReceipt;
import buildingtheev.charging.stations.management.system.rw.entity.ChargingSession;
import buildingtheev.charging.stations.management.system.rw.entity.SessionStatus;
import buildingtheev.charging.stations.management.system.rw.repository.ChargeReceiptRepository;
import buildingtheev.charging.stations.management.system.rw.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChargeReceiptService {
    private final ChargeReceiptRepository repo;
    private final ChargingSessionRepository sessionRepo;
    public ChargeReceiptService(ChargeReceiptRepository repo, ChargingSessionRepository sessionRepo) {
        this.repo = repo; this.sessionRepo = sessionRepo;
    }
    public String save(UUID sid, ChargeReceipt r) {
        Optional<ChargingSession> s = sessionRepo.findById(sid);
        if (s.isEmpty()) return "The charging session with that id does not exist";
        if (s.get().getStatus() != SessionStatus.FINISHED) return "Charge receipts are only for FINISHED charging sessions";
        r.setIssuedDate(LocalDate.now());
        r.setChargingSession(s.get());
        repo.save(r);
        return "Charge receipt saved successfully";
    }
    public List<ChargeReceipt> findAll() { return repo.findAll(); }
    public Optional<ChargeReceipt> findById(UUID id) { return repo.findById(id); }
    public ChargeReceipt update(UUID id, ChargeReceipt r) {
        ChargeReceipt e = repo.findById(id).orElseThrow();
        e.setTariffName(r.getTariffName()); e.setRemarks(r.getRemarks()); e.setEnergyKwh(r.getEnergyKwh());
        return repo.save(e);
    }
    public boolean delete(UUID id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
    public List<ChargeReceipt> findByDriverPlate(String p) { return repo.findByDriverPlate(p); }
}
