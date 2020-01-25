package org.emartos.mediaconverter.functionaltests.v1.model;



import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediaConverterClient {

    private static final String MEDIA_CONVERTER_DOCKER_ENDPOINT = "http://172.17.0.1:8086";
    private static final String MEDIA_CONVERTER_ENDPOINT = "http://localhost:8080";
    private static final String MEDIA_CONVERTER_API_KEY = "d08da773-bae1-4589-bed8-828075c54f5c";
    private static final String RESIZE_IMAGE_ENDPOINT = "/image/resize";
    private static final String PROCESS_IMAGE_ENDPOINT = "/image/process";
    private static final String HEALTH_CHECK_ENDPOINT = "/health-check";
    private static final String AUTOROTATE_IMAGE_ENDPOINT = "/image/autorotate";
private static final String TEST_IMG_PATH = "functional-tests/src/test/resources/testimg.jpg";

    public static void main(String[] args) throws IOException {

        final String uri = MEDIA_CONVERTER_ENDPOINT + RESIZE_IMAGE_ENDPOINT;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("apiKey", MEDIA_CONVERTER_API_KEY);
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.IMAGE_JPEG);
        headers.setAccept(acceptableMediaTypes);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        Integer width = 100;
        Integer height = 100;
//        MultipartFile multipartFile = getValidImage();
//        Resource resource = multipartFile.getResource();

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("width", width);
        parts.add("height", height);
        parts.add("file", getValidFile());

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parts, headers);

        HttpEntity<Resource> response = restTemplate.postForEntity(
                uri,
                httpEntity,
                Resource.class);

        //        String response = healthCheckRequest();
        System.out.println(response);
    }

    private static MultipartFile getValidImage() throws IOException {
        File file = new File(TEST_IMG_PATH);
        FileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length(), file.getParentFile());
        fileItem.getOutputStream();
        return new CommonsMultipartFile(fileItem);
    }

    private static Resource getValidFile() throws IOException {
        File file = new File(TEST_IMG_PATH);
        return new FileSystemResource(file);
    }

    private static String healthCheckRequest() {
        final String uri = MEDIA_CONVERTER_ENDPOINT + HEALTH_CHECK_ENDPOINT;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}
