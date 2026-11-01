package buildingtheev.charging.stations.management.system.rw.controller;

import buildingtheev.charging.stations.management.system.rw.entity.ChargingPoint;
import buildingtheev.charging.stations.management.system.rw.service.ChargingPointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/charging-points")
public class ChargingPointController {
    private final ChargingPointService service;
    public ChargingPointController(ChargingPointService s) { this.service = s; }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ChargingPoint p) { return new ResponseEntity<>(service.save(p), HttpStatus.CREATED); }
    @GetMapping("/all")
    public List<ChargingPoint> all() { return service.findAll(); }
    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable UUID id) {
        Optional<ChargingPoint> p = service.findById(id);
        if (p.isEmpty()) return new ResponseEntity<>("The charging point with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(p.get(), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ChargingPoint p) {
        if (service.findById(id).isEmpty()) return new ResponseEntity<>("The charging point with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(service.update(id, p), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (!service.delete(id)) return new ResponseEntity<>("The charging point with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Charging point deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/by-connector-type")
    public List<ChargingPoint> byConnectorType(@RequestParam String name) { return service.findByConnectorTypeName(name); }
    @PutMapping("/{cpId}/connector-types/{ctId}")
    public ResponseEntity<String> addConnectorType(@PathVariable UUID cpId, @PathVariable UUID ctId) {
        String msg = service.addConnectorType(cpId, ctId);
        if (msg.equals("Connector type added to the charging point")) return new ResponseEntity<>(msg, HttpStatus.OK);
        if (msg.equals("The charging point with that id does not exist") || msg.equals("That connector type does not exist")) return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }
    @PatchMapping("/{cpId}/bay")
    public ResponseEntity<String> changeBay(@PathVariable UUID cpId, @RequestParam int bayNumber) {
        String msg = service.changeBay(cpId, bayNumber);
        if (msg.equals("Bay changed successfully")) return new ResponseEntity<>(msg, HttpStatus.OK);
        if (msg.equals("The charging point with that id does not exist") || msg.equals("The bay with that bayNumber does not exist")) return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }
}
