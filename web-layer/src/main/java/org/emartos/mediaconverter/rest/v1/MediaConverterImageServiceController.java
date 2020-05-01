package org.emartos.mediaconverter.rest.v1;


import org.emartos.mediaconverter.MediaConverterImageService;
import org.emartos.mediaconverter.config.PropertiesConfig;
import org.emartos.mediaconverter.rest.v1.utils.image.ImageServiceControllerValidationHelper;
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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class MediaConverterImageServiceController implements org.emartos.mediaconverterapi.v1.MediaConverterService {

    private static final Logger LOGGER = Logger.getLogger(MediaConverterImageServiceController.class.getName());

    @Autowired
    private MediaConverterImageService mediaConverterImageService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Override
    public ResponseEntity<Resource> resizeImage(String apiKey, MultipartFile file, Integer width, Integer height)
            throws BadRequestException {
        validateApiKey(apiKey);
        try {
            validateResizeImage(new ResizeFileUploadForm(file.getBytes(), width, height));
            return buildResponseEntity(mediaConverterImageService.resizeImage(file.getBytes(), width, height));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO Exception", e);
        }
        return null;
    }

    @Override
    public ResponseEntity<Resource> autorotateImage(String apiKey, MultipartFile file) throws BadRequestException {
        validateApiKey(apiKey);
        try {
            validateImage(new FileUploadForm(file.getBytes()));
            return buildResponseEntity(mediaConverterImageService.autorotateImage(file.getBytes()));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO Exception", e);
        }
        return null;
    }

    @Override
    public HashMap<String, String> healthCheck() {
        HashMap<String, String> healthCheck = new HashMap<>();
        healthCheck.put("health-check", "OK");
        return healthCheck;
    }

    private void validateApiKey(String apiKey) throws BadRequestException {
        if (apiKey == null || !propertiesConfig.getApiKeys().contains(apiKey)) {
            throw new BadRequestException("Invalid API key: " + apiKey);
        }
    }

    private void validateResizeImage(ResizeFileUploadForm resizeFileUploadForm) throws BadRequestException {
        new ImageServiceControllerValidationHelper("MediaConverterImageService")
                .checkValidResolutionRange(resizeFileUploadForm.getWidth(), "width")
                .checkValidResolutionRange(resizeFileUploadForm.getHeight(), "height")
                .checkValidImage(resizeFileUploadForm.getFileData(), "fileData");
    }

    private void validateImage(FileUploadForm fileUploadForm) throws BadRequestException {
        new ImageServiceControllerValidationHelper("MediaConverterImageService")
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

