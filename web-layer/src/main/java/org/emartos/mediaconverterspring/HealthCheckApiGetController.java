package org.emartos.mediaconverterspring;

import org.emartos.mediaconverterapi.v1.MediaConverterService;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HealthCheckApiGetController implements MediaConverterService {

    @Override
    public HashMap<String, String> indexApi() {
        HashMap<String, String> status = new HashMap<>();
        status.put("application", "custom");
        status.put("status", "ok-v4!");

        return status;
    }
}
