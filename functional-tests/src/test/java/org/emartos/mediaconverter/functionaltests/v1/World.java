package org.emartos.mediaconverter.functionaltests.v1;

import cucumber.runtime.java.guice.ScenarioScoped;
import org.emartos.mediaconverter.functionaltests.v1.model.MediaConverterClient;
import org.emartos.mediaconverter.functionaltests.v1.model.ResponseImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ScenarioScoped
public class World {
    private final List<Exception> exceptions = new ArrayList<>();
    private final Map<String, MediaConverterClient> clients = new HashMap<>();
    private final Map<String, String> apiKeys = new HashMap<>();

    private ResponseImage responseImage;

//    private static final String MEDIA_CONVERTER_ENDPOINT = "http://172.17.0.1:8086/media-converter";
    private static final String MEDIA_CONVERTER_ENDPOINT = "http://localhost:8080/media-converter";

    public void addClient(String reference, MediaConverterClient mediaConverterClient) {
        clients.put(reference, mediaConverterClient);
    }

    public MediaConverterClient getClient(String reference) {
        return clients.get(reference);
    }

    public void addException(Exception exception) {
        exceptions.add(exception);
    }

    public boolean hasException(Class<? extends  Exception> exceptionClass) {
        for (Exception exception : exceptions) {
            if (exceptionClass.isInstance(exception)) {
                return true;
            }
        }
        return false;
    }

    public String getMediaConverterEndpoint() { return MEDIA_CONVERTER_ENDPOINT; }

    public String getApiKey(String clientReference) {
        return apiKeys.get(clientReference  );
    }

    public void setApiKey(String clientReference, String apiKey){
        apiKeys.put(clientReference, apiKey);
    }

    public ResponseImage getResponseImage() {
        return responseImage;
    }

    public void setResponseImage(ResponseImage responseImage) {
        this.responseImage = responseImage;
    }
}

