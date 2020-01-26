package org.emartos.mediaconverter.functionaltests.v1.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FileUtils {

    private static final String TMP_IMG_PATH = "src/test/resources/images/tmp/";//getTmpFilename
    private static final int RANDOM_UPPER_LIMIT = 9999999;

    private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

    private FileUtils() {
        // Prevent instantiation
    }

    public static StringBuilder getTmpFilename() {
        return new StringBuilder()
                .append(ZonedDateTime.now().toInstant().toEpochMilli())
                .append(ThreadLocalRandom.current().nextInt(RANDOM_UPPER_LIMIT));
    }

    public static Optional<File> createFile(byte[] image) throws IOException {
        String inputPath = TMP_IMG_PATH + getTmpFilename();
        LOGGER.log(Level.INFO, "\"Start creating file with input path:" + inputPath);

        try (FileOutputStream fileOutputStream = new FileOutputStream(inputPath)) {
            fileOutputStream.write(image);
            return Optional.of(new File(inputPath));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File not found error", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO error", e);
        }
        return Optional.empty();
    }
}
