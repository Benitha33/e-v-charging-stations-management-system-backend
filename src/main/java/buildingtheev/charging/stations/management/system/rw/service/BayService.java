package buildingtheev.charging.stations.management.system.rw.service;

import buildingtheev.charging.stations.management.system.rw.entity.Bay;
import buildingtheev.charging.stations.management.system.rw.repository.BayRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BayService {
    private final BayRepository repo;
    public BayService(BayRepository repo) { this.repo = repo; }
    public Bay save(Bay b) { return repo.save(b); }
    public List<Bay> findAll() { return repo.findAll(); }
    public Optional<Bay> findById(UUID id) { return repo.findById(id); }
    public Bay update(UUID id, Bay b) {
        Bay e = repo.findById(id).orElseThrow();
        e.setBayNumber(b.getBayNumber());
        e.setName(b.getName());
        return repo.save(e);
    }
    public boolean delete(UUID id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
    public List<Bay> findFreeBays() { return repo.findFreeBays(); }
    public Optional<Bay> findByBayNumber(int n) { return repo.findByBayNumber(n); }
}
