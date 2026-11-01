package buildingtheev.charging.stations.management.system.rw.controller;

import buildingtheev.charging.stations.management.system.rw.entity.Driver;
import buildingtheev.charging.stations.management.system.rw.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService service;
    public DriverController(DriverService s) { this.service = s; }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Driver d) { return new ResponseEntity<>(service.save(d), HttpStatus.CREATED); }
    @GetMapping("/all")
    public List<Driver> all() { return service.findAll(); }
    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable UUID id) {
        Optional<Driver> d = service.findById(id);
        if (d.isEmpty()) return new ResponseEntity<>("The driver with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(d.get(), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Driver d) {
        if (service.findById(id).isEmpty()) return new ResponseEntity<>("The driver with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(service.update(id, d), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (!service.delete(id)) return new ResponseEntity<>("The driver with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Driver deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/by-last-name")
    public List<Driver> byLastName(@RequestParam String lastName) { return service.findByLastName(lastName); }
    @GetMapping("/of-charging-point/{cpId}")
    public ResponseEntity<?> ofChargingPoint(@PathVariable UUID cpId) {
        Optional<List<Driver>> result = service.findDriversOfChargingPoint(cpId);
        if (result.isEmpty()) return new ResponseEntity<>("The charging point with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }
}
