package buildingtheev.charging.stations.management.system.rw.service;

import buildingtheev.charging.stations.management.system.rw.entity.Driver;
import buildingtheev.charging.stations.management.system.rw.repository.ChargingPointRepository;
import buildingtheev.charging.stations.management.system.rw.repository.DriverRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {
    private final DriverRepository repo;
    private final ChargingPointRepository cpRepo;
    public DriverService(DriverRepository repo, ChargingPointRepository cpRepo) { this.repo = repo; this.cpRepo = cpRepo; }
    public Driver save(Driver d) { return repo.save(d); }
    public List<Driver> findAll() { return repo.findAll(); }
    public Optional<Driver> findById(UUID id) { return repo.findById(id); }
    public Driver update(UUID id, Driver d) {
        Driver e = repo.findById(id).orElseThrow();
        e.setFirstName(d.getFirstName()); e.setLastName(d.getLastName());
        e.setPlateNumber(d.getPlateNumber()); e.setEmail(d.getEmail()); e.setPhone(d.getPhone());
        return repo.save(e);
    }
    public boolean delete(UUID id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
    public List<Driver> findByLastName(String n) { return repo.findByLastNameIgnoreCaseOrderByFirstNameAsc(n); }
    public Optional<List<Driver>> findDriversOfChargingPoint(UUID cpId) {
        if (!cpRepo.existsById(cpId)) return Optional.empty();
        return Optional.of(repo.findDriversOfChargingPoint(cpId));
    }
}
