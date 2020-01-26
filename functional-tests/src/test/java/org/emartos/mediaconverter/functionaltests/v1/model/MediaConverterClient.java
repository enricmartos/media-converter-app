package org.emartos.mediaconverter.functionaltests.v1.model;

import org.emartos.mediaconverter.functionaltests.v1.model.request.MediaConverterRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class MediaConverterClient {

    private static final String MEDIA_CONVERTER_API_KEY_FIELD_KEY =  "apiKey";

    private String mediaConverterEndpoint;
    private HttpHeaders headers;
    private MultiValueMap<String, Object> multipartFormBody;
    HttpEntity<Resource> response;

    public MediaConverterClient(String mediaConverterEndpoint) {
        this.mediaConverterEndpoint = mediaConverterEndpoint;
    }

    public void setHeaders(String apiKey, MediaType contentType) {
        headers = new HttpHeaders();
        headers.add(MEDIA_CONVERTER_API_KEY_FIELD_KEY, apiKey);
        headers.setContentType(contentType);
    }

    public void setMultipartFormBody(MediaConverterRequest mediaConverterRequest) throws IOException {
        this.multipartFormBody = mediaConverterRequest.getMultipartFormBody();
    }

    public void doRestRequest(String mediaConverterPath, HttpMethod httpMethod) {

        final String URI = mediaConverterEndpoint + mediaConverterPath;
        RestTemplate restClient = new RestTemplate();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multipartFormBody, headers);

        response = restClient.exchange(
                URI,
                httpMethod,
                httpEntity,
                Resource.class);
    }

}
