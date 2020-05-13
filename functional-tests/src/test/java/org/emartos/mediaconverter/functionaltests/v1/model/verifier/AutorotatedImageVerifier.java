package org.emartos.mediaconverter.functionaltests.v1.model.verifier;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import org.emartos.mediaconverter.functionaltests.v1.model.ResponseImage;
import org.emartos.mediaconverter.functionaltests.v1.model.utils.EXIFOrientationType;
import org.emartos.mediaconverter.functionaltests.v1.model.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class AutorotatedImageVerifier {

    private String expectedOrientation;

    public String getExpectedOrientation() {
        return expectedOrientation;
    }

    public void setExpectedOrientation(String expectedOrientation) {
        this.expectedOrientation = expectedOrientation;
    }

    public void verifyImageOrientation(ResponseImage responseImage) throws IOException, ImageProcessingException, MetadataException {
        if (expectedOrientation != null) {
            Optional<EXIFOrientationType> actualResponseImageOrientation = getImageOrientation(responseImage.getResponseImageBytes());
            actualResponseImageOrientation.ifPresent(i ->
                assertEquals(actualResponseImageOrientation.get().name(), expectedOrientation)
            );
        }
    }

    private Optional<EXIFOrientationType> getImageOrientation(byte[] autorotatedImage) throws IOException, ImageProcessingException, MetadataException {
        Optional<File> fileOptional = FileUtils.createFile(autorotatedImage);

        if (fileOptional.isPresent()) {
            Metadata metadata = ImageMetadataReader.readMetadata(fileOptional.get());
            ExifIFD0Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            if (exifIFD0Directory != null) {
                int orientation = exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                EXIFOrientationType exifOrientationType = EXIFOrientationType.getByType(orientation);
                return Optional.of(exifOrientationType);
            } else {
                return Optional.of(EXIFOrientationType.AUTOROTATED);
            }
        }
        return Optional.empty();
    }



}
