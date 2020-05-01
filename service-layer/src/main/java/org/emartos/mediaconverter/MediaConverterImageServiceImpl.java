package org.emartos.mediaconverter;


import org.emartos.mediaconverter.utils.ConvertUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class MediaConverterImageServiceImpl implements MediaConverterImageService {

    //Constants
    //Common
    private static final String ROOT_DIR = "/tmp/";
    private static final Logger LOGGER = Logger.getLogger(MediaConverterImageServiceImpl.class.getName());
    //resizeImage
    private static final String SUFFIX_RESIZED_IMAGE_PATTERN = "_%dx%d";
    private static final String RESIZE_COMMAND_PATTERN = "convert %s -resize %dx%d! %s";
    //autorotate
    private static final String SUFFIX_AUTOROTATED_IMAGE_PATTERN = "_autorotated";
    private static final String AUTOROTATE_COMMAND_PATTERN = "convert %s -auto-orient %s";

    @Override
    public byte[] resizeImage(byte[] image, Integer width, Integer height) {
        try {
            StringBuilder tmpFilename = ConvertUtils.getTmpFilename();
            String inputPath = ROOT_DIR + tmpFilename;
            String suffixResizedImage = String.format(SUFFIX_RESIZED_IMAGE_PATTERN, width, height);
            String outputPath = ROOT_DIR + tmpFilename + suffixResizedImage;
            ConvertUtils.createFile(image, inputPath);
            String resizeCommand = String.format(RESIZE_COMMAND_PATTERN, inputPath, width, height, outputPath);
            if(ConvertUtils.convertWithCommand(resizeCommand)) {
                byte[] imageResized = Files.readAllBytes(new File(outputPath).toPath());
                Files.delete(new File(outputPath).toPath());
                Files.delete(new File(inputPath).toPath());
                LOGGER.log(Level.INFO, "Image resized");
                return imageResized;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO error", e);
        }
        return null;
    }

    @Override
    public byte[] autorotateImage(byte[] image) {
        try {
            StringBuilder tmpFilename = ConvertUtils.getTmpFilename();
            String inputPath = ROOT_DIR + tmpFilename;
            String outputPath = ROOT_DIR + tmpFilename + SUFFIX_AUTOROTATED_IMAGE_PATTERN;
            ConvertUtils.createFile(image, inputPath);
            String autorotateCommand = String.format(AUTOROTATE_COMMAND_PATTERN, inputPath, outputPath);
            if(ConvertUtils.convertWithCommand(autorotateCommand)) {
                byte[] imageAutorotated = Files.readAllBytes(new File(outputPath).toPath());
                Files.delete(new File(outputPath).toPath());
                Files.delete(new File(inputPath).toPath());

                return imageAutorotated;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO error", e);
        }
        return null;
    }

}
