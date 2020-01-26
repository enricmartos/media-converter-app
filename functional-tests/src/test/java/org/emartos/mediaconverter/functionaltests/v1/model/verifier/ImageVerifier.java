package org.emartos.mediaconverter.functionaltests.v1.model.verifier;


import org.emartos.mediaconverter.functionaltests.v1.model.ResponseImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.testng.Assert.assertEquals;

public abstract class ImageVerifier {

    private static final String GROUND_TRUTH_IMG_PATH = "src/test/resources/images/groundtruth/";

    private String expectedResponseImage;

    public String getExpectedResponseImage() {
        return expectedResponseImage;
    }

    public void setExpectedResponseImage(String expectedResponseImage) {
        this.expectedResponseImage = expectedResponseImage;
    }

    public void verifyImage(ResponseImage responseImage) throws IOException {
        if (expectedResponseImage != null) {
            byte[] expectedImage = Files.readAllBytes(new File(GROUND_TRUTH_IMG_PATH
                    + expectedResponseImage).toPath());
            assertEquals(responseImage.getResponseImageBytes(), expectedImage);
        }
    }


}
