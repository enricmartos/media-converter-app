package org.emartos.mediaconverter.functionaltests.v1.model.request;


import org.apache.commons.lang3.ArrayUtils;
import org.emartos.mediaconverter.functionaltests.v1.model.utils.FileUtils;
import org.emartos.mediaconverter.functionaltests.v1.random.ImageRandomizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

abstract class ImageRequest implements MediaConverterRequest {

    private static final String RANDOM_ORIGINAL_IMAGE = "randomImage";
//    private static final String TEST_IMG_PATH = "functional-tests/src/test/resources/testimg.jpg";
    private static final String INPUT_IMG_PATH = "src/test/resources/images/input/";
    private static final String FILE_REQUEST_FIELD_KEY = "selectedFile";

    String originalImage;

    MultiValueMap<String, Object> getBaseMultipartFormBody() {
        Resource resource = null;
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        if (originalImage.equals(RANDOM_ORIGINAL_IMAGE)) {
            byte[] fileData = ArrayUtils.toPrimitive(new ImageRandomizer().getRandomValue());
            Optional<File> fileOptional = FileUtils.createFile(fileData);
            if (fileOptional.isPresent()) {
                resource = new FileSystemResource(fileOptional.get());
            }
        } else if (!originalImage.isEmpty()) {
            File file = new File(INPUT_IMG_PATH + originalImage);
            resource = new FileSystemResource(file);
        }
        body.add(FILE_REQUEST_FIELD_KEY, resource);
        return body;
    }
}
