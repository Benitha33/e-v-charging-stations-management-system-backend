package buildingtheev.charging.stations.management.system.rw.service;

import buildingtheev.charging.stations.management.system.rw.entity.Bay;
import buildingtheev.charging.stations.management.system.rw.entity.ChargingPoint;
import buildingtheev.charging.stations.management.system.rw.entity.ConnectorType;
import buildingtheev.charging.stations.management.system.rw.repository.BayRepository;
import buildingtheev.charging.stations.management.system.rw.repository.ChargingPointRepository;
import buildingtheev.charging.stations.management.system.rw.repository.ConnectorTypeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChargingPointService {
    private final ChargingPointRepository repo;
    private final ConnectorTypeRepository ctRepo;
    private final BayRepository bayRepo;
    public ChargingPointService(ChargingPointRepository repo, ConnectorTypeRepository ctRepo, BayRepository bayRepo) {
        this.repo = repo; this.ctRepo = ctRepo; this.bayRepo = bayRepo;
    }
    public ChargingPoint save(ChargingPoint p) { return repo.save(p); }
    public List<ChargingPoint> findAll() { return repo.findAll(); }
    public Optional<ChargingPoint> findById(UUID id) { return repo.findById(id); }
    public ChargingPoint update(UUID id, ChargingPoint p) {
        ChargingPoint e = repo.findById(id).orElseThrow();
        e.setCode(p.getCode()); e.setBrand(p.getBrand()); e.setPowerKw(p.getPowerKw()); e.setBay(p.getBay());
        return repo.save(e);
    }
    public boolean delete(UUID id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
    public List<ChargingPoint> findByConnectorTypeName(String name) { return repo.findByConnectorTypeName(name); }
    public String addConnectorType(UUID cpId, UUID ctId) {
        Optional<ChargingPoint> cp = repo.findById(cpId);
        if (cp.isEmpty()) return "The charging point with that id does not exist";
        Optional<ConnectorType> ct = ctRepo.findById(ctId);
        if (ct.isEmpty()) return "That connector type does not exist";
        ChargingPoint p = cp.get();
        if (p.getConnectorTypes().contains(ct.get())) return "The charging point already has that connector type";
        p.getConnectorTypes().add(ct.get());
        repo.save(p);
        return "Connector type added to the charging point";
    }
    public String changeBay(UUID cpId, int bayNumber) {
        Optional<ChargingPoint> cp = repo.findById(cpId);
        if (cp.isEmpty()) return "The charging point with that id does not exist";
        Optional<Bay> b = bayRepo.findByBayNumber(bayNumber);
        if (b.isEmpty()) return "The bay with that bayNumber does not exist";
        if (repo.existsByBay(b.get())) return "That bay is taken by another charging point";
        ChargingPoint p = cp.get();
        p.setBay(b.get());
        repo.save(p);
        return "Bay changed successfully";
    }
}
