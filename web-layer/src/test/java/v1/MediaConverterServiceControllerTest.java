//package v1;
//
//
//import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;
//import org.emartos.mediaconverterapi.v1.model.FileUploadForm;
//import org.emartos.mediaconverterapi.v1.model.ResizeFileUploadForm;
//import org.emartos.mediaconverterspring.MediaConverterService;
//import org.emartos.mediaconverterspring.config.PropertiesConfig;
//import org.emartos.mediaconverterspring.rest.v1.MediaConverterServiceController;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.powermock.api.mockito.PowerMockito.when;
//
//
//@RunWith(PowerMockRunner.class)
//public class MediaConverterServiceControllerTest {
//
//    private static final String TEST_IMG_PATH = "src/test/resources/testimg.jpg";
//
//    //Resolution of a HD image (horizontal)
//    private static final Integer VALID_IMG_WIDTH = 1280;
//
//    private static final Integer VALID_IMG_HEIGHT = 720;
//
//    //Resolution of a 8K image (horizontal)
//    private static final Integer EXCESSIVE_IMG_WIDTH= 7680;
//
//    private static final Integer EXCESSIVE_IMG_HEIGHT = 4320;
//
//    //Invalid image resolution
//    private static final Integer NEGATIVE_IMG_RESOLUTION= -1;
//
//
//    @InjectMocks
//    MediaConverterServiceController imageOptimizerServiceController;
//    @Mock
//    MediaConverterService imageOptimizerService;
//    @Mock
//    private PropertiesConfig propertiesConfig;
//
//    @Rule
//    private final ExpectedException thrown = ExpectedException.none();
//
//    @Before
//    public void setUp() throws Exception {
//        List<String> apiKeys = new ArrayList<>();
//        apiKeys.add("apiKey");
//        when(propertiesConfig.getApiKeys()).thenReturn(apiKeys);
//    }
//
//
//    @Test
//    public void testValidImageWithEmptyFile() throws BadRequestException {
//        when(imageOptimizerService.resizeImage(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
//
//        Response response = imageOptimizerServiceController.resizeImage("apiKey",
//                getStubValidImage(), VALID_IMG_WIDTH, VALID_IMG_HEIGHT);
//
//        Assert.assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    public void testValidImageWithPresentFile() throws BadRequestException, IOException {
//        when(imageOptimizerService.resizeImage(Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(getStubFile());
//
//        Response response = imageOptimizerServiceController.resizeImage("apiKey",
//                getStubFileUploadForm(getStubValidImage(), VALID_IMG_WIDTH, VALID_IMG_HEIGHT));
//
//        Assert.assertEquals(OK.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    public void testInvalidImageWithInvalidFileData() throws BadRequestException {
//        thrown.expect(BadRequestException.class);
//        imageOptimizerServiceController.resizeImage("apiKey",
//                getStubFileUploadForm(getStubInvalidFile(), VALID_IMG_WIDTH, VALID_IMG_HEIGHT));
//    }
//
//    @Test
//    public void testValidImageWithExcessiveResolution() throws BadRequestException {
//        thrown.expect(BadRequestException.class);
//        imageOptimizerServiceController.resizeImage("apiKey",
//                getStubFileUploadForm(getStubValidImage(), EXCESSIVE_IMG_WIDTH, EXCESSIVE_IMG_HEIGHT));
//    }
//
//    @Test
//    public void testValidImageWithNegativeResolution() throws BadRequestException {
//        thrown.expect(BadRequestException.class);
//        imageOptimizerServiceController.resizeImage("apiKey",
//                getStubFileUploadForm(getStubValidImage(), NEGATIVE_IMG_RESOLUTION, NEGATIVE_IMG_RESOLUTION));
//    }
//
//
//    private FileUploadForm getStubFileUploadForm(byte[] fileData) {
//        return new FileUploadForm(fileData);
//    }
//
//    private ResizeFileUploadForm getStubFileUploadForm(byte[] fileData, Integer width, Integer height) {
//        return new ResizeFileUploadForm(fileData, width, height);
//    }
//
//    private byte[] getStubFile() throws IOException {
//        return Files.readAllBytes(new File(TEST_IMG_PATH).toPath());
//    }
//
//    //Valid Image (JPG header)
//    private byte[] getStubValidImage() {
//        return new byte[]{-1, -40, -1, -32};
//    }
//
//    //Invalid Image (GIF header)
//    private byte[] getStubInvalidFile() {
//        return new byte[]{47, 49, 46, 38};
//    }
//}