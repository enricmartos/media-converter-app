package org.emartos.mediaconverter.functionaltests.v1.model;

import org.apache.commons.io.IOUtils;
import org.emartos.mediaconverter.functionaltests.v1.model.request.MediaConverterRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MediaConverterClient {

    private static final Logger LOGGER = Logger.getLogger(MediaConverterClient.class.getName());
    private static final String MEDIA_CONVERTER_API_KEY_FIELD_KEY =  "apiKey";

    private String mediaConverterEndpoint;
    private HttpHeaders headers;
    private MultiValueMap<String, Object> multipartFormBody;
    private ResponseEntity<Resource> response;

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

        try {
            response = restClient.exchange(
                    URI,
                    httpMethod,
                    httpEntity,
                    Resource.class);
        } catch (HttpStatusCodeException exception){
            response = new ResponseEntity<>(exception.getStatusCode());
        }
    }

    public HttpStatus getResponseStatusCode() {
        return response.getStatusCode();
    }

    public byte[] getResponseImage() {
        try {
            if (response.getBody() != null) {
                InputStream inputStream = response.getBody().getInputStream();
                return IOUtils.toByteArray(inputStream);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO Exception");
        }
        return null;
    }
}
