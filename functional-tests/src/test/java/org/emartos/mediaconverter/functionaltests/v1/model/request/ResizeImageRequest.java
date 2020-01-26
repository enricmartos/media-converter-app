package org.emartos.mediaconverter.functionaltests.v1.model.request;

import org.springframework.util.MultiValueMap;

import java.io.IOException;

public class ResizeImageRequest extends ImageRequest {

    private static final String WIDTH_REQUEST_FIELD_KEY = "width";
    private static final String HEIGHT_REQUEST_FIELD_KEY = "height";

    @Override
    public MultiValueMap<String, Object> getMultipartFormBody() throws IOException {
        MultiValueMap<String, Object> body = getBaseMultipartFormBody();
        //tmp
        Integer width = 100;
        Integer height = 100;
        body.add(WIDTH_REQUEST_FIELD_KEY, width);
        body.add(HEIGHT_REQUEST_FIELD_KEY, height);
        return body;
    }
}
