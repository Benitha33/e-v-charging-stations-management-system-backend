package buildingtheev.charging.stations.management.system.rw.service;

import buildingtheev.charging.stations.management.system.rw.entity.ConnectorType;
import buildingtheev.charging.stations.management.system.rw.repository.ConnectorTypeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConnectorTypeService {
    private final ConnectorTypeRepository repo;
    public ConnectorTypeService(ConnectorTypeRepository repo) { this.repo = repo; }
    public ConnectorType save(ConnectorType c) { return repo.save(c); }
    public List<ConnectorType> findAll() { return repo.findAll(); }
    public Optional<ConnectorType> findById(UUID id) { return repo.findById(id); }
    public ConnectorType update(UUID id, ConnectorType c) {
        ConnectorType e = repo.findById(id).orElseThrow();
        e.setName(c.getName());
        return repo.save(e);
    }
    public boolean delete(UUID id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
    public List<ConnectorType> findUnused() { return repo.findUnused(); }
}
