package org.emartos.mediaconverter.functionaltests.v1.model.request;

import org.springframework.util.MultiValueMap;

import java.io.IOException;

public interface MediaConverterRequest {

    MultiValueMap<String, Object> getMultipartFormBody() throws IOException;
}
