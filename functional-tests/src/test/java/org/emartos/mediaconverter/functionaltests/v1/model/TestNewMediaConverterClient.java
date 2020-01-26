package org.emartos.mediaconverter.functionaltests.v1.model;

import org.emartos.mediaconverter.functionaltests.v1.model.request.MediaConverterRequest;
import org.emartos.mediaconverter.functionaltests.v1.model.request.ResizeImageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.io.IOException;

public class TestNewMediaConverterClient {

    private static final String MEDIA_CONVERTER_DOCKER_ENDPOINT = "http://172.17.0.1:8086";
    private static final String MEDIA_CONVERTER_ENDPOINT = "http://localhost:8080";
    private static final String MEDIA_CONVERTER_API_KEY = "d08da773-bae1-4589-bed8-828075c54f5c";
    private static final String RESIZE_IMAGE_ENDPOINT = "/image/resize";
    private static final String AUTOROTATE_IMAGE_ENDPOINT = "/image/autorotate";
    private static final String TEST_IMG_PATH = "functional-tests/src/test/resources/testimg.jpg";

    public static void main(String[] args) throws IOException {
        MediaConverterClient mediaConverterClient = new MediaConverterClient(MEDIA_CONVERTER_ENDPOINT);
        mediaConverterClient.setHeaders(MEDIA_CONVERTER_API_KEY, MediaType.MULTIPART_FORM_DATA);
        MediaConverterRequest mediaConverterRequest = new ResizeImageRequest();
        mediaConverterClient.setMultipartFormBody(mediaConverterRequest);
        mediaConverterClient.doRestRequest(RESIZE_IMAGE_ENDPOINT, HttpMethod.POST);
    }
}
