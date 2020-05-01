package org.emartos.mediaconverter.rest.v1.utils.video;


import org.emartos.mediaconverter.rest.v1.utils.ServiceControllerValidationHelper;
import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;

public class VideoServiceControllerValidationHelper {
    private final String videoServiceControllerName;
    private static final Integer MIN_TIME = 0;
    private static final Integer MAX_TIME = 59;

    public VideoServiceControllerValidationHelper(String videoServiceControllerName) {
        this.videoServiceControllerName = videoServiceControllerName;
    }

    public VideoServiceControllerValidationHelper checkValidTimeRange(Integer value, String parameterName) throws BadRequestException {
        if (value < MIN_TIME || value > MAX_TIME) {
            throw new BadRequestException(String.format("%s field %s can't be less than %d and greater than %d", videoServiceControllerName,
                    parameterName, MIN_TIME, MAX_TIME));
        }
        return this;
    }

    public VideoServiceControllerValidationHelper checkValidVideo(byte[] array, String parameterName) throws BadRequestException {
        ServiceControllerValidationHelper.checkNotNull(array, parameterName, videoServiceControllerName);
        ServiceControllerValidationHelper.checkNotEmpty(array, parameterName, videoServiceControllerName);
        if(!VideoType.isVideo(array)) {
            throw new BadRequestException(String.format("%s field %s is not a valid video", videoServiceControllerName,
                    parameterName));
        }
        return this;
    }







}
