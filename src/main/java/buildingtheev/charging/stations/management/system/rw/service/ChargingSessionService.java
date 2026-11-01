package buildingtheev.charging.stations.management.system.rw.service;

import buildingtheev.charging.stations.management.system.rw.entity.ChargingSession;
import buildingtheev.charging.stations.management.system.rw.entity.SessionStatus;
import buildingtheev.charging.stations.management.system.rw.repository.ChargeReceiptRepository;
import buildingtheev.charging.stations.management.system.rw.repository.ChargingSessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChargingSessionService {
    private final ChargingSessionRepository repo;
    private final ChargeReceiptRepository receiptRepo;
    public ChargingSessionService(ChargingSessionRepository repo, ChargeReceiptRepository receiptRepo) {
        this.repo = repo; this.receiptRepo = receiptRepo;
    }
    public String save(ChargingSession s) {
        if (s.getSessionDate() != null && s.getSessionDate().isBefore(LocalDate.now()))
            return "Charging session date cannot be in the past";
        if (s.getChargingPoint() != null && s.getSessionDate() != null
            && repo.existsByChargingPointAndSessionDateAndStatusNot(s.getChargingPoint(), s.getSessionDate(), SessionStatus.CANCELLED))
            return "Charging point is already reserved on that date";
        s.setStatus(SessionStatus.RESERVED);
        repo.save(s);
        return "Charging session saved successfully";
    }
    public List<ChargingSession> findAll() { return repo.findAll(); }
    public Optional<ChargingSession> findById(UUID id) { return repo.findById(id); }
    public ChargingSession update(UUID id, ChargingSession s) {
        ChargingSession e = repo.findById(id).orElseThrow();
        e.setSessionDate(s.getSessionDate()); e.setVehicleModel(s.getVehicleModel());
        e.setDriver(s.getDriver()); e.setChargingPoint(s.getChargingPoint());
        return repo.save(e);
    }
    public String delete(UUID id) {
        Optional<ChargingSession> s = repo.findById(id);
        if (s.isEmpty()) return "The charging session with that id does not exist";
        if (receiptRepo.existsByChargingSession(s.get())) return "That charging session has charge receipts, delete them first";
        repo.deleteById(id);
        return "Charging session deleted successfully";
    }
    public List<ChargingSession> findByStatus(SessionStatus st) { return repo.findByStatusOrderBySessionDateAsc(st); }
    public List<ChargingSession> findBetween(LocalDate s, LocalDate e) { return repo.findBySessionDateBetweenOrderBySessionDateAsc(s, e); }
    public String changeStatus(UUID id, SessionStatus ns) {
        Optional<ChargingSession> opt = repo.findById(id);
        if (opt.isEmpty()) return "The charging session with that id does not exist";
        ChargingSession s = opt.get();
        SessionStatus c = s.getStatus();
        boolean ok = (c == SessionStatus.RESERVED && (ns == SessionStatus.CHARGING || ns == SessionStatus.CANCELLED))
                  || (c == SessionStatus.CHARGING && (ns == SessionStatus.FINISHED || ns == SessionStatus.CANCELLED));
        if (!ok) return "Cannot change status from " + c + " to " + ns;
        s.setStatus(ns);
        repo.save(s);
        return "Charging session status updated successfully";
    }
    public List<Object[]> statsByStatus() { return repo.countByStatus(); }
    @Transactional
    public int cancelDay(UUID id, LocalDate d) { return repo.cancelDay(id, d); }
    public Page<ChargingSession> findPage(Pageable p) { return repo.findAll(p); }
}
