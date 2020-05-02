package org.emartos.mediaconverter.rest.v1;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

abstract class ServiceControllerHelper {


    static ResponseEntity<Resource> buildResponseEntity(byte[] resourceConverted, String contentTypeValue,
                                                        String contentDispositionValue) {
        return resourceConverted != null ?
                ResponseEntity.ok()
                        .headers(getHttpHeaders(contentTypeValue, contentDispositionValue))
                        .body(new ByteArrayResource(resourceConverted)) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static HttpHeaders getHttpHeaders(String contentTypeValue, String contentDispositionValue) {
        MultiValueMap<String, String> headersMap = new LinkedMultiValueMap<>();
        headersMap.add(CONTENT_DISPOSITION, contentDispositionValue);
        headersMap.add(CONTENT_TYPE, contentTypeValue);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(headersMap);
        return httpHeaders;
    }
}
