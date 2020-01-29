package org.emartos.mediaconverter.functionaltests.v1.model.request;

import org.springframework.util.MultiValueMap;

import java.io.IOException;

public class ResizeImageRequest extends ImageRequest {

    private static final String WIDTH_REQUEST_FIELD_KEY = "width";
    private static final String HEIGHT_REQUEST_FIELD_KEY = "height";

    private final String width;
    private final String height;

    public ResizeImageRequest(String originalImage, String width, String height) {
        this.originalImage = originalImage;
        this.width = width;
        this.height = height;
    }

    @Override
    public MultiValueMap<String, Object> getMultipartFormBody() throws IOException {
        MultiValueMap<String, Object> body = getBaseMultipartFormBody();
        body.add(WIDTH_REQUEST_FIELD_KEY, width);
        body.add(HEIGHT_REQUEST_FIELD_KEY, height);
        return body;
    }
}
