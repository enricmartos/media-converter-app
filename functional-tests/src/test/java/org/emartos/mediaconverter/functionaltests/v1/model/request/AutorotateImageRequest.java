package org.emartos.mediaconverter.functionaltests.v1.model.request;

import org.springframework.util.MultiValueMap;

import java.io.IOException;

public class AutorotateImageRequest extends ImageRequest {

    @Override
    public MultiValueMap<String, Object> getMultipartFormBody() throws IOException {
        return getBaseMultipartFormBody();
    }
}
