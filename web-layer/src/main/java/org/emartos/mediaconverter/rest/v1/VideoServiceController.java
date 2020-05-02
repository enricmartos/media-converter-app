package org.emartos.mediaconverter.rest.v1;


import org.emartos.mediaconverter.VideoService;
import org.emartos.mediaconverter.config.PropertiesConfig;
import org.emartos.mediaconverter.rest.v1.utils.video.VideoServiceControllerValidationHelper;
import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class VideoServiceController implements org.emartos.mediaconverterapi.v1.VideoService {

    private static final Logger LOGGER = Logger.getLogger(VideoServiceController.class.getName());
    private static final String CONTENT_TYPE_VALUE = "video/mp4";
    private static final String CONTENT_DISPOSITION_VALUE = "attachment";

    @Autowired
    private VideoService videoService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Override
    public ResponseEntity<Resource> trimVideo(String apiKey, MultipartFile file,
                                              Integer startMinute, Integer startSecond,
                                              Integer endMinute, Integer endSecond) throws BadRequestException {
        validateApiKey(apiKey);
        try {
            validateTrimVideo(file.getBytes(), startMinute, startSecond, endMinute, endSecond);
            byte[] trimmedVideo = videoService.trimVideo(file.getBytes(), startMinute, startSecond, endMinute, endSecond);
            return ServiceControllerHelper.buildResponseEntity(trimmedVideo, CONTENT_TYPE_VALUE, CONTENT_DISPOSITION_VALUE);
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
        new VideoServiceControllerValidationHelper("VideoService")
                .checkValidTimeRange(startMinute, "startMinute")
                .checkValidTimeRange(startSecond, "startSecond")
                .checkValidTimeRange(endMinute, "endMinute")
                .checkValidTimeRange(endSecond, "endSecond")
                .checkValidVideo(video, "selectedFile");

    }
}

