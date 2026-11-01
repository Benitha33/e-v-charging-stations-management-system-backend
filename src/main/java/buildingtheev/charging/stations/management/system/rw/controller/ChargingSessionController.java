package buildingtheev.charging.stations.management.system.rw.controller;

import buildingtheev.charging.stations.management.system.rw.entity.ChargingSession;
import buildingtheev.charging.stations.management.system.rw.entity.SessionStatus;
import buildingtheev.charging.stations.management.system.rw.service.ChargingSessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/charging-sessions")
public class ChargingSessionController {
    private final ChargingSessionService service;
    public ChargingSessionController(ChargingSessionService s) { this.service = s; }
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ChargingSession s) {
        String msg = service.save(s);
        if (msg.equals("Charging session saved successfully")) return new ResponseEntity<>(msg, HttpStatus.CREATED);
        if (msg.equals("Charging point is already reserved on that date")) return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/all")
    public List<ChargingSession> all() { return service.findAll(); }
    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable UUID id) {
        Optional<ChargingSession> s = service.findById(id);
        if (s.isEmpty()) return new ResponseEntity<>("The charging session with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(s.get(), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ChargingSession s) {
        if (service.findById(id).isEmpty()) return new ResponseEntity<>("The charging session with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(service.update(id, s), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String msg = service.delete(id);
        if (msg.equals("Charging session deleted successfully")) return new ResponseEntity<>(msg, HttpStatus.OK);
        if (msg.equals("That charging session has charge receipts, delete them first")) return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/by-status")
    public List<ChargingSession> byStatus(@RequestParam SessionStatus status) { return service.findByStatus(status); }
    @GetMapping("/between")
    public List<ChargingSession> between(@RequestParam String start, @RequestParam String end) {
        return service.findBetween(LocalDate.parse(start), LocalDate.parse(end));
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(@PathVariable UUID id, @RequestParam SessionStatus status) {
        String msg = service.changeStatus(id, status);
        if (msg.equals("Charging session status updated successfully")) return new ResponseEntity<>(msg, HttpStatus.OK);
        if (msg.equals("The charging session with that id does not exist")) return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/stats/by-status")
    public List<Object[]> statsByStatus() { return service.statsByStatus(); }
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID chargingPointId, @RequestParam String date) {
        int count = service.cancelDay(chargingPointId, LocalDate.parse(date));
        return new ResponseEntity<>(count + " charging sessions cancelled", HttpStatus.OK);
    }
    @GetMapping("/page")
    public Page<ChargingSession> page(@RequestParam int page, @RequestParam int size, @RequestParam String sort) {
        String[] parts = sort.split(",");
        Sort.Direction dir = parts.length > 1 && parts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, parts[0]));
        return service.findPage(pageable);
    }
}
