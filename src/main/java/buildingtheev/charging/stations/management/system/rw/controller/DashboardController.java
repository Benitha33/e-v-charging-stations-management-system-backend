package buildingtheev.charging.stations.management.system.rw.controller;

import buildingtheev.charging.stations.management.system.rw.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;
    public DashboardController(DashboardService s) { this.service = s; }
    @GetMapping("/summary")
    public Map<String, Long> summary() { return service.summary(); }
}
