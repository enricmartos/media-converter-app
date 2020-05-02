package org.emartos.mediaconverter.rest.v1;


import org.emartos.mediaconverter.ImageService;
import org.emartos.mediaconverter.config.PropertiesConfig;
import org.emartos.mediaconverter.rest.v1.utils.image.ImageServiceControllerValidationHelper;
import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;
import org.emartos.mediaconverterapi.v1.model.FileUploadForm;
import org.emartos.mediaconverterapi.v1.model.ResizeFileUploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class ImageServiceController implements org.emartos.mediaconverterapi.v1.ImageService {

    private static final Logger LOGGER = Logger.getLogger(ImageServiceController.class.getName());
    private static final String CONTENT_DISPOSITION_VALUE = "inline";

    @Autowired
    private ImageService imageService;

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Override
    public ResponseEntity<Resource> resizeImage(String apiKey, MultipartFile file, Integer width, Integer height)
            throws BadRequestException {
        validateApiKey(apiKey);
        try {
            validateResizeImage(new ResizeFileUploadForm(file.getBytes(), width, height));
            byte[] resizedImage = imageService.resizeImage(file.getBytes(), width, height);
            return ServiceControllerHelper.buildResponseEntity(resizedImage, MediaType.IMAGE_JPEG_VALUE, CONTENT_DISPOSITION_VALUE);
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
            byte[] autorotatedImage = imageService.autorotateImage(file.getBytes());
            return ServiceControllerHelper.buildResponseEntity(autorotatedImage, MediaType.IMAGE_JPEG_VALUE, CONTENT_DISPOSITION_VALUE);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO Exception", e);
        }
        return null;
    }

    private void validateApiKey(String apiKey) throws BadRequestException {
        if (apiKey == null || !propertiesConfig.getApiKeys().contains(apiKey)) {
            throw new BadRequestException("Invalid API key: " + apiKey);
        }
    }

    private void validateResizeImage(ResizeFileUploadForm resizeFileUploadForm) throws BadRequestException {
        new ImageServiceControllerValidationHelper("ImageService")
                .checkValidResolutionRange(resizeFileUploadForm.getWidth(), "width")
                .checkValidResolutionRange(resizeFileUploadForm.getHeight(), "height")
                .checkValidImage(resizeFileUploadForm.getFileData(), "fileData");
    }

    private void validateImage(FileUploadForm fileUploadForm) throws BadRequestException {
        new ImageServiceControllerValidationHelper("ImageService")
                .checkValidImage(fileUploadForm.getFileData(), "fileData");
    }

}

