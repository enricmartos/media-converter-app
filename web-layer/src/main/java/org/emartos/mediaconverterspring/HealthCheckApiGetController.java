package org.emartos.mediaconverterspring;

import org.emartos.mediaconverterapi.v1.MediaConverterService;
import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
//public class HealthCheckApiGetController implements MediaConverterService {
public class HealthCheckApiGetController {

//    @Override
    public HashMap<String, String> indexApi() {
        HashMap<String, String> status = new HashMap<>();
        status.put("application", "custom");
        status.put("status", "ok-v4!");

        return status;
    }


}
