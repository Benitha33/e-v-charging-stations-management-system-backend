package buildingtheev.charging.stations.management.system.rw.controller;

import buildingtheev.charging.stations.management.system.rw.entity.Bay;
import buildingtheev.charging.stations.management.system.rw.service.BayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bays")
public class BayController {
    private final BayService service;
    public BayController(BayService s) { this.service = s; }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Bay b) { return new ResponseEntity<>(service.save(b), HttpStatus.CREATED); }
    @GetMapping("/all")
    public List<Bay> all() { return service.findAll(); }
    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable UUID id) {
        Optional<Bay> b = service.findById(id);
        if (b.isEmpty()) return new ResponseEntity<>("The bay with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(b.get(), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Bay b) {
        if (service.findById(id).isEmpty()) return new ResponseEntity<>("The bay with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(service.update(id, b), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (!service.delete(id)) return new ResponseEntity<>("The bay with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Bay deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/free")
    public List<Bay> free() { return service.findFreeBays(); }
}
