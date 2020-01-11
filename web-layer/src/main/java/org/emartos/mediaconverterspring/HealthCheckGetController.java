package org.emartos.mediaconverterspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HealthCheckGetController {

    @Autowired
    HealthCheckService healthCheckService;

    @GetMapping("/health-check")
    public HashMap<String, String> index() {
        HashMap<String, String> status = new HashMap<>();
        status.put("application", "custom");
        String healthCheckStatus = healthCheckService.getHealthCheckStatus();
        status.put("status", healthCheckStatus);

        return status;
    }
}
