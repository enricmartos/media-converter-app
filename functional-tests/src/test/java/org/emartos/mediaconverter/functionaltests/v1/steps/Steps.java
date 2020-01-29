package org.emartos.mediaconverter.functionaltests.v1.steps;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.emartos.mediaconverter.functionaltests.v1.World;
import org.emartos.mediaconverter.functionaltests.v1.model.MediaConverterClient;
import org.emartos.mediaconverter.functionaltests.v1.model.ResponseImage;
import org.emartos.mediaconverter.functionaltests.v1.model.request.AutorotateImageRequest;
import org.emartos.mediaconverter.functionaltests.v1.model.request.ResizeImageRequest;
import org.emartos.mediaconverter.functionaltests.v1.model.verifier.AutorotatedImageVerifier;
import org.emartos.mediaconverter.functionaltests.v1.model.verifier.ResizedImageVerifier;
import org.emartos.mediaconverter.functionaltests.v1.model.exceptions.BadRequestException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.Assert;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Random;


public class Steps {

    private static final String MEDIA_CONVERTER_API_KEY = "d08da773-bae1-4589-bed8-828075c54f5c";
    private static final String RESIZE_IMAGE_ENDPOINT = "/api/v1/image/resize";
    private static final String AUTOROTATE_IMAGE_ENDPOINT = "/api/v1/image/autorotate";
    private static final Integer API_KEY_LENGTH = 36;
    private World world;

    @Inject
    public Steps(World world) {
        this.world = world;
    }

    @Given("^([^ ]+) is a client of the media-converter module$")
    public void createClient(String clientReference) throws Throwable {
        MediaConverterClient mediaConverterClient = new MediaConverterClient(this.world.getMediaConverterEndpoint());
        this.world.addClient(clientReference, mediaConverterClient);
        this.world.setApiKey(clientReference, MEDIA_CONVERTER_API_KEY);
    }

    @Given("^([^ ]+) has an invalid api key$")
    public void createInvalidApiKey(String clientReference) {
        Random rand = new Random();
        int randomInt = rand.nextInt(API_KEY_LENGTH);
        String apiKeyRandom = Integer.toString(randomInt);
        world.setApiKey(clientReference, apiKeyRandom);
    }

    @When("^([^ ]+) requests to resize an image")
    public void getImageResized(String clientReference, List<ResizeImageRequest> resizeImageRequests) throws Throwable {
        MediaConverterClient mediaConverterClient = this.world.getClient(clientReference);
        mediaConverterClient.setHeaders(MEDIA_CONVERTER_API_KEY, MediaType.MULTIPART_FORM_DATA);
        mediaConverterClient.setMultipartFormBody(resizeImageRequests.get(0));
        mediaConverterClient.doRestRequest(RESIZE_IMAGE_ENDPOINT, HttpMethod.POST);
        if (mediaConverterClient.getResponseStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            Exception badRequestException = new BadRequestException("Bad Request Exception");
            this.world.addException(badRequestException);
        } else {
            world.setResponseImage(new ResponseImage(mediaConverterClient.getResponseImage()));
        }
    }

    @When("^([^ ]+) requests to auto-rotate an image")
    public void getImageAutorotated(String clientReference, List<AutorotateImageRequest> autorotateImageRequests) throws
            Throwable {
        MediaConverterClient mediaConverterClient = this.world.getClient(clientReference);
        mediaConverterClient.setHeaders(MEDIA_CONVERTER_API_KEY, MediaType.MULTIPART_FORM_DATA);
        mediaConverterClient.setMultipartFormBody(autorotateImageRequests.get(0));
        mediaConverterClient.doRestRequest(AUTOROTATE_IMAGE_ENDPOINT, HttpMethod.POST);
        if (mediaConverterClient.getResponseStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            Exception badRequestException = new BadRequestException("Bad Request Exception");
            this.world.addException(badRequestException);
        } else {
            world.setResponseImage(new ResponseImage(mediaConverterClient.getResponseImage()));
        }
    }

    @Then("^the media-converter module returns the image resized$")
    public void verifyImageResized(List<ResizedImageVerifier> verifiers) throws IOException {
            verifiers.get(0).verifyImageDimension(world.getResponseImage());
    }

    @Then("^the media-converter module returns the image auto-rotated$")
    public void verifyImageAutorotated(List<AutorotatedImageVerifier> verifiers) throws
            IOException,
            ImageProcessingException,
            MetadataException {
        if (verifiers.get(0).getExpectedResponseImage() == null) {
            verifiers.get(0).verifyImageOrientation(world.getResponseImage());
        } else {
            verifiers.get(0).verifyImage(world.getResponseImage());
        }
    }

    @Then("^the request fails with a bad request$")
    public void badRequest() {
        Assert.assertTrue(world.hasException(BadRequestException.class));
    }


}
