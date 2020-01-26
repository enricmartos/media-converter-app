package org.emartos.mediaconverter.functionaltests.v1.model.request;


import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;

abstract class ImageRequest implements MediaConverterRequest {

    private static final String TEST_IMG_PATH = "functional-tests/src/test/resources/testimg.jpg";
    private static final String FILE_REQUEST_FIELD_KEY = "selectedFile";



    MultiValueMap<String, Object> getBaseMultipartFormBody() throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(FILE_REQUEST_FIELD_KEY, getValidFile());
        return body;
    }

    private static Resource getValidFile() throws IOException {
        File file = new File(TEST_IMG_PATH);
        return new FileSystemResource(file);
    }
}
