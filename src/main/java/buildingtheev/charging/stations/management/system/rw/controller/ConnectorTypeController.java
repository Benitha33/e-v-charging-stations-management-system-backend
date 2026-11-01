package buildingtheev.charging.stations.management.system.rw.controller;

import buildingtheev.charging.stations.management.system.rw.entity.ConnectorType;
import buildingtheev.charging.stations.management.system.rw.service.ConnectorTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/connector-types")
public class ConnectorTypeController {
    private final ConnectorTypeService service;
    public ConnectorTypeController(ConnectorTypeService s) { this.service = s; }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ConnectorType c) { return new ResponseEntity<>(service.save(c), HttpStatus.CREATED); }
    @GetMapping("/all")
    public List<ConnectorType> all() { return service.findAll(); }
    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable UUID id) {
        Optional<ConnectorType> c = service.findById(id);
        if (c.isEmpty()) return new ResponseEntity<>("The connector type with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(c.get(), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ConnectorType c) {
        if (service.findById(id).isEmpty()) return new ResponseEntity<>("The connector type with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(service.update(id, c), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (!service.delete(id)) return new ResponseEntity<>("The connector type with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Connector type deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/unused")
    public List<ConnectorType> unused() { return service.findUnused(); }
}
