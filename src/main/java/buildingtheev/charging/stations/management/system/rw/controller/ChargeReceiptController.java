package buildingtheev.charging.stations.management.system.rw.controller;

import buildingtheev.charging.stations.management.system.rw.entity.ChargeReceipt;
import buildingtheev.charging.stations.management.system.rw.service.ChargeReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/charge-receipts")
public class ChargeReceiptController {
    private final ChargeReceiptService service;
    public ChargeReceiptController(ChargeReceiptService s) { this.service = s; }
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID chargingSessionId, @RequestBody ChargeReceipt r) {
        String msg = service.save(chargingSessionId, r);
        if (msg.equals("Charge receipt saved successfully")) return new ResponseEntity<>(msg, HttpStatus.CREATED);
        if (msg.equals("The charging session with that id does not exist")) return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/all")
    public List<ChargeReceipt> all() { return service.findAll(); }
    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable UUID id) {
        Optional<ChargeReceipt> r = service.findById(id);
        if (r.isEmpty()) return new ResponseEntity<>("The charge receipt with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(r.get(), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ChargeReceipt r) {
        if (service.findById(id).isEmpty()) return new ResponseEntity<>("The charge receipt with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(service.update(id, r), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        if (!service.delete(id)) return new ResponseEntity<>("The charge receipt with that id does not exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Charge receipt deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/driver/{plateNumber}")
    public List<ChargeReceipt> byDriver(@PathVariable String plateNumber) { return service.findByDriverPlate(plateNumber); }
}
