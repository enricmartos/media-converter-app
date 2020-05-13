package org.emartos.mediaconverter.rest.v1;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

final class MultipartFileMother  {

    private static final Logger LOGGER = Logger.getLogger(MultipartFile.class.getName());

    private static final String TEST_IMG_PATH = "src/test/resources/image/testimg.jpg";
    private static final String TEST_GIF_PATH = "src/test/resources/shared/testGif.gif";
    private static final String TEST_VIDEO_PATH = "src/test/resources/video/testVideo.mp4";

    // Valid Image (JPG header)
    static MultipartFile jpegImage() {
        File file = new File(TEST_IMG_PATH);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException", e);
        }
        return new MockMultipartFile("file", file.getName(), "image/jpeg", bytes);
    }

    //Invalid Image (GIF header)
    static MultipartFile gifImage() {
        File file = new File(TEST_GIF_PATH);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException", e);
        }
        return new MockMultipartFile("file", file.getName(), "image/jpeg", bytes);
    }

    static MultipartFile mp4Video() {
        File file = new File(TEST_VIDEO_PATH);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOException", e);
        }
        return new MockMultipartFile("file", file.getName(), "video/mp4", bytes);
    }
}
