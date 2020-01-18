package org.emartos.mediaconverter.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConvertUtils {

    //Constants
    //Common
    private static final Logger LOGGER = Logger.getLogger(ConvertUtils.class.getName());
    //getTmpFilename
    private static final int RANDOM_UPPER_LIMIT = 9999999;

    public static StringBuilder getTmpFilename() {
        Random rand = new Random();
        int n = rand.nextInt(RANDOM_UPPER_LIMIT);
        StringBuilder tmpFilename = new StringBuilder();
        tmpFilename.append(ZonedDateTime.now().toInstant().toEpochMilli()).append(n);
        return tmpFilename;
    }

    public static Optional<File> createFile(byte[] image, String path) throws IOException {
        LOGGER.log(Level.INFO, "\"Start creating file with input path:" + path);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(image);
            return Optional.of(new File(path));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File not found error", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO error", e);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
        return Optional.empty();
    }

    public static boolean convertWithCommand(String command) {
        try {
            LOGGER.log(Level.INFO, "Init conversion");
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", command);
            Process process = processBuilder.start();
            if (process.waitFor() == 0) {
                return true;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IOError executing convert command", e);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Convert command execution interrupted", e);
        }
        return false;
    }
}
