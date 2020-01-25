package org.emartos.mediaconverter.rest.v1;


import org.emartos.mediaconverter.MediaConverterService;
import org.emartos.mediaconverter.config.PropertiesConfig;
import org.emartos.mediaconverter.rest.v1.utils.ServiceControllerValidationHelper;
import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;
import org.emartos.mediaconverterapi.v1.model.FileUploadForm;
import org.emartos.mediaconverterapi.v1.model.ResizeFileUploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class MediaConverterServiceController {

    private static final Logger LOGGER = Logger.getLogger(MediaConverterServiceController.class.getName());

    @Autowired
    private MediaConverterService mediaConverterService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @GetMapping("health-check")
    public String getHealthCheck() {
        return "Health check OK";
    }

    @PostMapping("image/process")
    public ResponseEntity<Resource> processValues(@RequestHeader("apiKey") String apiKey,
                                                  @RequestParam("width") Integer width, @RequestParam("height") Integer height) throws BadRequestException  {
        validateApiKey(apiKey);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @PostMapping("/image/resize")
//    @RequestMapping(value="/image/resize", method=RequestMethod.POST , consumes= {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> resizeImage(@RequestHeader("apiKey") String apiKey, @RequestParam("file") MultipartFile file,
                                         @RequestParam("width") Integer width, @RequestParam("height") Integer height)
            throws BadRequestException {
        try {
            validateApiKey(apiKey);
            validateResizeImage(new ResizeFileUploadForm(file.getBytes(), width, height));
            return buildResponseEntity(mediaConverterService.resizeImage(file.getBytes(), width, height));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO Exception");
        }
        return null;
    }

    @PostMapping("/image/autorotate")
    public ResponseEntity<Resource> autorotateImage(@RequestHeader("apiKey") String apiKey,
                                                    @RequestParam("file") MultipartFile file) throws BadRequestException {
        try {
            validateApiKey(apiKey);
            validateImage(new FileUploadForm(file.getBytes()));
            return buildResponseEntity(mediaConverterService.autorotateImage(file.getBytes()));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO Exception");
        }
        return null;
    }

    private void validateApiKey(String apiKey) throws BadRequestException {
        if (apiKey == null || !propertiesConfig.getApiKeys().contains(apiKey)) {
            throw new BadRequestException("Invalid API key: " + apiKey);
        }
    }

    private void validateResizeImage(ResizeFileUploadForm resizeFileUploadForm) throws BadRequestException {
        new ServiceControllerValidationHelper("MediaConverterService")
                .checkValidRange(resizeFileUploadForm.getWidth(), "width")
                .checkValidRange(resizeFileUploadForm.getHeight(), "height")
                .checkValidImage(resizeFileUploadForm.getFileData(), "fileData");
    }

    private void validateImage(FileUploadForm fileUploadForm) throws BadRequestException {
        new ServiceControllerValidationHelper("MediaConverterService")
                .checkValidImage(fileUploadForm.getFileData(), "fileData");
    }

    private ResponseEntity<Resource> buildResponseEntity(byte[] imageConverted) {
        return imageConverted != null ?
                ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("image/jpeg"))
                        .body(new ByteArrayResource(imageConverted)) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

