package org.emartos.mediaconverterspring;

import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class HealthCheckGetController {

    private static final Logger LOGGER = Logger.getLogger(HealthCheckGetController.class.getName());

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
