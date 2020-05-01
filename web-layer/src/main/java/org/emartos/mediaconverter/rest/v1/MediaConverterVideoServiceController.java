package org.emartos.mediaconverter.rest.v1;


import org.emartos.mediaconverter.MediaConverterVideoService;
import org.emartos.mediaconverter.config.PropertiesConfig;
import org.emartos.mediaconverter.rest.v1.utils.video.VideoServiceControllerValidationHelper;
import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class MediaConverterVideoServiceController implements org.emartos.mediaconverterapi.v1.MediaConverterVideoService {

    private static final Logger LOGGER = Logger.getLogger(MediaConverterVideoServiceController.class.getName());

    @Autowired
    private MediaConverterVideoService mediaConverterVideoService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Override
    public ResponseEntity<Resource> trimVideo(String apiKey, MultipartFile file,
                                              Integer startMinute, Integer startSecond,
                                              Integer endMinute, Integer endSecond) throws BadRequestException {
        validateApiKey(apiKey);
        try {
            validateTrimVideo(file.getBytes(), startMinute, startSecond, endMinute, endSecond);
            return buildResponseEntity(mediaConverterVideoService.trimVideo(file.getBytes(), startMinute, startSecond, endMinute, endSecond));
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

    private void validateTrimVideo(byte[] video, Integer startMinute, Integer startSecond,
                                   Integer endMinute, Integer endSecond) throws BadRequestException {
        new VideoServiceControllerValidationHelper("MediaConverterVideoService")
                .checkValidTimeRange(startMinute, "startMinute")
                .checkValidTimeRange(startSecond, "startSecond")
                .checkValidTimeRange(endMinute, "endMinute")
                .checkValidTimeRange(endSecond, "endSecond")
                .checkValidVideo(video, "selectedFile");

    }

    private ResponseEntity<Resource> buildResponseEntity(byte[] imageConverted) {
        return imageConverted != null ?
                ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("video/mp4"))
                        .body(new ByteArrayResource(imageConverted)) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

