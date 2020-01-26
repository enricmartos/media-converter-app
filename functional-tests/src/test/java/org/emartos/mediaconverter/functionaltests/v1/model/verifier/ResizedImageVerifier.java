package org.emartos.mediaconverter.functionaltests.v1.model.verifier;

import org.emartos.mediaconverter.functionaltests.v1.model.ResponseImage;
import org.testng.Assert;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class ResizedImageVerifier extends ImageVerifier {

    private Integer expectedWidth;
    private Integer expectedHeight;

    public Integer getExpectedWidth() {
        return expectedWidth;
    }

    public void setExpectedWidth(Integer expectedWidth) {
        this.expectedWidth = expectedWidth;
    }

    public Integer getExpectedHeight() {
        return expectedHeight;
    }

    public void setExpectedHeight(int expectedHeight) {
        this.expectedHeight = expectedHeight;
    }


    public void verifyImageDimension(ResponseImage responseImage) throws IOException {
        if (expectedWidth != null && expectedHeight != null) {
            Dimension expectedImageDimension = new Dimension(expectedWidth, expectedHeight);
            Optional<Dimension> actualResponseImageDimension = getImageDimension(responseImage.getResponseImageBytes());
//            assertEquals(actualResponseImageDimension.get(), expectedImageDimension);
            actualResponseImageDimension.ifPresent(i ->
                    assertEquals(actualResponseImageDimension.get(), expectedImageDimension)
            );
            Assert.assertTrue(actualResponseImageDimension.isPresent());
        }
    }

    private Optional<Dimension> getImageDimension(byte[] resizedImage) throws IOException {

        ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(resizedImage));

        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            try {
                reader.setInput(iis);
                return Optional.of(new Dimension(reader.getWidth(0), reader.getHeight(0)));
            } finally {
                reader.dispose();
            }
        }
        return Optional.empty();
    }



}

