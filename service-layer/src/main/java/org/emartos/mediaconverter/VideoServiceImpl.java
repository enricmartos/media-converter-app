package org.emartos.mediaconverter;

import org.emartos.mediaconverter.utils.ConvertUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class VideoServiceImpl implements VideoService {

    //Constants
    //Common
    private static final String ROOT_DIR = "/tmp/";
    private static final Logger LOGGER = Logger.getLogger(VideoServiceImpl.class.getName());
    // trim video
    private static final String SUFFIX_TRIMMED_VIDEO_PATTERN = "_%d:%d-%d:%d";
    private static final String VIDEO_FORMAT = ".mp4";
    private static final String TRIM_COMMAND_PATTERN = "ffmpeg -i %s -ss 00:%d:%d -t 00:%d:%d -c copy %s";

    @Override
    public byte[] trimVideo(byte[] video, Integer startMinute, Integer startSecond, Integer endMinute, Integer endSecond) {
        try {
            StringBuilder tmpFilename = ConvertUtils.getTmpFilename();
            String inputPath = ROOT_DIR + tmpFilename;
            String suffixTrimmedVideo = String.format(SUFFIX_TRIMMED_VIDEO_PATTERN, startMinute, startSecond, endMinute, endSecond);
            String suffixTrimmedVideoWithFormat = suffixTrimmedVideo + VIDEO_FORMAT;
            String outputPath = ROOT_DIR + tmpFilename + suffixTrimmedVideoWithFormat;
            ConvertUtils.createFile(video, inputPath);
            String trimCommand = String.format(TRIM_COMMAND_PATTERN, inputPath, startMinute, startSecond, endMinute, endSecond, outputPath);
            if(ConvertUtils.convertWithCommand(trimCommand)) {
                byte[] videoTrimmed = Files.readAllBytes(new File(outputPath).toPath());
                Files.delete(new File(outputPath).toPath());
                Files.delete(new File(inputPath).toPath());
                LOGGER.log(Level.INFO, "Video trimmed");
                return videoTrimmed;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO error", e);
        }
        return null;
    }
}
