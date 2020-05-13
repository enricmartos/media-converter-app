package org.emartos.mediaconverter.rest.v1;


import org.emartos.mediaconverter.ImageService;
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

import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
public class ImageServiceControllerTest {

    // Resolution of a HD image (horizontal)
    private static final Integer VALID_IMG_WIDTH = 1280;
    private static final Integer VALID_IMG_HEIGHT = 720;

    // Resolution of a 8K image (horizontal)
    private static final Integer EXCESSIVE_IMG_WIDTH = 7680;
    private static final Integer EXCESSIVE_IMG_HEIGHT = 4320;

    // Invalid image resolution
    private static final Integer NEGATIVE_IMG_RESOLUTION = -1;

    private static final Integer OUTPUT_FILE_LENGTH = 4;

    private static final String API_KEY_MOCK = "apiKey";

    @InjectMocks
    ImageServiceController imageServiceController;
    @Mock
    ImageService imageService;
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
    public void testValidImageWithEmptyOutputFile() throws BadRequestException {
        when(imageService.resizeImage(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

        ResponseEntity<Resource> response = imageServiceController.resizeImage(API_KEY_MOCK,
                MultipartFileMother.jpegImage(), VALID_IMG_WIDTH, VALID_IMG_HEIGHT);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testValidImageWithPresentOutputFile() throws BadRequestException {
        when(imageService.resizeImage(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new byte[OUTPUT_FILE_LENGTH]);

        ResponseEntity<Resource> response = imageServiceController.resizeImage(API_KEY_MOCK,
                MultipartFileMother.jpegImage(), VALID_IMG_WIDTH, VALID_IMG_HEIGHT);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testInvalidImageWithInvalidFileData() throws BadRequestException {
        thrown.expect(BadRequestException.class);
        imageServiceController.resizeImage(API_KEY_MOCK, MultipartFileMother.gifImage(), VALID_IMG_WIDTH, VALID_IMG_HEIGHT);
    }

    @Test
    public void testValidImageWithExcessiveResolution() throws BadRequestException {
        thrown.expect(BadRequestException.class);
        imageServiceController.resizeImage(API_KEY_MOCK,
                MultipartFileMother.jpegImage(), EXCESSIVE_IMG_WIDTH, EXCESSIVE_IMG_HEIGHT);
    }

    @Test
    public void testValidImageWithNegativeResolution() throws BadRequestException {
        thrown.expect(BadRequestException.class);
        imageServiceController.resizeImage(API_KEY_MOCK,
                MultipartFileMother.jpegImage(), NEGATIVE_IMG_RESOLUTION, NEGATIVE_IMG_RESOLUTION);
    }

}