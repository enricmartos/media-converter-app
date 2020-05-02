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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
public class VideoServiceControllerTest {

    private static final String TEST_VIDEO_PATH = "src/test/resources/video/testVideo.mp4";
    private static final String TEST_GIF_PATH = "src/test/resources/shared/testGif.gif";

    // Valid time ranges
    private static final Integer START_MINUTE = 0;
    private static final Integer START_SECOND = 1;
    private static final Integer END_MINUTE = 0;
    private static final Integer END_SECOND = 3;

    // Invalid time ranges
    private static final Integer NEGATIVE_TIME = -1;
    private static final Integer EXCESSIVE_TIME = 60;

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
    public void testValidVideoWithEmptyOutputFile() throws BadRequestException, IOException {
        when(videoService.trimVideo(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

        ResponseEntity<Resource> response = videoServiceController.trimVideo(API_KEY_MOCK,
                getStubValidVideo(), START_MINUTE, START_SECOND, END_MINUTE, END_SECOND);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testValidVideoWithPresentOutputFile() throws BadRequestException, IOException {
        when(videoService.trimVideo(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt(),
                Mockito.anyInt(), Mockito.anyInt())).thenReturn(getStubFile());

        ResponseEntity<Resource> response = videoServiceController.trimVideo(API_KEY_MOCK,
                getStubValidVideo(), START_MINUTE, START_SECOND, END_MINUTE, END_SECOND);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testInvalidVideoWithInvalidFileData() throws BadRequestException, IOException {
        thrown.expect(BadRequestException.class);
        videoServiceController.trimVideo(API_KEY_MOCK, getStubInvalidVideo(),
                START_MINUTE, START_SECOND, END_MINUTE, END_SECOND);
    }

    @Test
    public void testValidVideoWithExcessiveTimeRange() throws BadRequestException, IOException {
        thrown.expect(BadRequestException.class);
        videoServiceController.trimVideo(API_KEY_MOCK, getStubValidVideo(),
                START_MINUTE, START_SECOND, EXCESSIVE_TIME, EXCESSIVE_TIME);
    }

    @Test
    public void testValidVideoWithNegativeTimeRange() throws BadRequestException, IOException {
        thrown.expect(BadRequestException.class);
        videoServiceController.trimVideo(API_KEY_MOCK, getStubValidVideo(),
                NEGATIVE_TIME, NEGATIVE_TIME, EXCESSIVE_TIME, EXCESSIVE_TIME);
    }



    private byte[] getStubFile() throws IOException {
        return Files.readAllBytes(new File(TEST_VIDEO_PATH).toPath());
    }

    //Valid Image (JPG header)
    private MultipartFile getStubValidVideo() throws IOException {
        File file = new File(TEST_VIDEO_PATH);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new MockMultipartFile("file", file.getName(), "video/mp4", bytes);
    }

    //Invalid Image (GIF header)
    private MultipartFile getStubInvalidVideo() throws IOException {
        File file = new File(TEST_GIF_PATH);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new MockMultipartFile("file", file.getName(), "image/jpeg", bytes);
    }

}