package org.emartos.mediaconverter.rest.v1;


import org.emartos.mediaconverter.VideoService;
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
public class VideoServiceControllerTest {

    // Valid time ranges
    private static final Integer START_MINUTE = 0;
    private static final Integer START_SECOND = 1;
    private static final Integer END_MINUTE = 0;
    private static final Integer END_SECOND = 3;

    // Invalid time ranges
    private static final Integer NEGATIVE_TIME = -1;
    private static final Integer EXCESSIVE_TIME = 60;

    private static final Integer OUTPUT_FILE_LENGTH = 4;

    private static final String API_KEY_MOCK = "apiKey";

    @InjectMocks
    VideoServiceController videoServiceController;
    @Mock
    VideoService videoService;
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
    public void testValidVideoWithEmptyOutputFile() throws BadRequestException {
        when(videoService.trimVideo(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

        ResponseEntity<Resource> response = videoServiceController.trimVideo(API_KEY_MOCK,
                MultipartFileMother.mp4Video(), START_MINUTE, START_SECOND, END_MINUTE, END_SECOND);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testValidVideoWithPresentOutputFile() throws BadRequestException {
        when(videoService.trimVideo(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt())).thenReturn(new byte[OUTPUT_FILE_LENGTH]);

        ResponseEntity<Resource> response = videoServiceController.trimVideo(API_KEY_MOCK,
                MultipartFileMother.mp4Video(), START_MINUTE, START_SECOND, END_MINUTE, END_SECOND);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testInvalidVideoWithInvalidFileData() throws BadRequestException {
        thrown.expect(BadRequestException.class);
        videoServiceController.trimVideo(API_KEY_MOCK, MultipartFileMother.gifImage(),
                START_MINUTE, START_SECOND, END_MINUTE, END_SECOND);
    }

    @Test
    public void testValidVideoWithExcessiveTimeRange() throws BadRequestException {
        thrown.expect(BadRequestException.class);
        videoServiceController.trimVideo(API_KEY_MOCK, MultipartFileMother.mp4Video(),
                START_MINUTE, START_SECOND, EXCESSIVE_TIME, EXCESSIVE_TIME);
    }

    @Test
    public void testValidVideoWithNegativeTimeRange() throws BadRequestException {
        thrown.expect(BadRequestException.class);
        videoServiceController.trimVideo(API_KEY_MOCK, MultipartFileMother.mp4Video(),
                NEGATIVE_TIME, NEGATIVE_TIME, EXCESSIVE_TIME, EXCESSIVE_TIME);
    }

}