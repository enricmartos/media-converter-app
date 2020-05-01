package org.emartos.mediaconverter.rest.v1;


import org.emartos.mediaconverter.MediaConverterImageService;
import org.emartos.mediaconverter.config.PropertiesConfig;
import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
public class MediaConverterImageServiceControllerTest {

    private static final String TEST_IMG_PATH = "src/test/resources/image/testimg.jpg";
    private static final String TEST_GIF_PATH = "src/test/resources/shared/testGif.gif";

    //Resolution of a HD image (horizontal)
    private static final Integer VALID_IMG_WIDTH = 1280;

    private static final Integer VALID_IMG_HEIGHT = 720;

    //Resolution of a 8K image (horizontal)
    private static final Integer EXCESSIVE_IMG_WIDTH= 7680;

    private static final Integer EXCESSIVE_IMG_HEIGHT = 4320;

    //Invalid image resolution
    private static final Integer NEGATIVE_IMG_RESOLUTION= -1;

    private static final String API_KEY_MOCK = "apiKey";

    @InjectMocks
    MediaConverterImageServiceController mediaConverterImageServiceController;
    @Mock
    MediaConverterImageService mediaConverterImageService;
    @Mock
    private PropertiesConfig propertiesConfig;

    @Rule
    private final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        List<String> apiKeys = new ArrayList<>();
        apiKeys.add(API_KEY_MOCK);
        when(propertiesConfig.getApiKeys()).thenReturn(apiKeys);
    }


    @Test
    public void testValidImageWithEmptyOutputFile() throws BadRequestException, IOException {
        when(mediaConverterImageService.resizeImage(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

        ResponseEntity<Resource> response = mediaConverterImageServiceController.resizeImage(API_KEY_MOCK,
                getStubValidImage(), VALID_IMG_WIDTH, VALID_IMG_HEIGHT);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testValidImageWithPresentOutputFile() throws BadRequestException, IOException {
        when(mediaConverterImageService.resizeImage(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(getStubFile());

        ResponseEntity<Resource> response = mediaConverterImageServiceController.resizeImage(API_KEY_MOCK,
                getStubValidImage(), VALID_IMG_WIDTH, VALID_IMG_HEIGHT);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testInvalidImageWithInvalidFileData() throws BadRequestException, IOException {
        thrown.expect(BadRequestException.class);
        mediaConverterImageServiceController.resizeImage(API_KEY_MOCK, getStubInvalidImage(), VALID_IMG_WIDTH, VALID_IMG_HEIGHT);
    }

    @Test
    public void testValidImageWithExcessiveResolution() throws BadRequestException, IOException {
        thrown.expect(BadRequestException.class);
        mediaConverterImageServiceController.resizeImage(API_KEY_MOCK,
                getStubValidImage(), EXCESSIVE_IMG_WIDTH, EXCESSIVE_IMG_HEIGHT);
    }

    @Test
    public void testValidImageWithNegativeResolution() throws BadRequestException, IOException {
        thrown.expect(BadRequestException.class);
        mediaConverterImageServiceController.resizeImage(API_KEY_MOCK,
                getStubValidImage(), NEGATIVE_IMG_RESOLUTION, NEGATIVE_IMG_RESOLUTION);
    }

    private byte[] getStubFile() throws IOException {
        return Files.readAllBytes(new File(TEST_IMG_PATH).toPath());
    }

    //Valid Image (JPG header)
    private MultipartFile getStubValidImage() throws IOException {
        File file = new File(TEST_IMG_PATH);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new MockMultipartFile("file", file.getName(), "image/jpeg", bytes);
    }

    //Invalid Image (GIF header)
    private MultipartFile getStubInvalidImage() throws IOException {
        File file = new File(TEST_GIF_PATH);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new MockMultipartFile("file", file.getName(), "image/jpeg", bytes);
    }

}