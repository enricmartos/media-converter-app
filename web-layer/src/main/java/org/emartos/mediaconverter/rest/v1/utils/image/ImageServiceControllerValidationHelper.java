package org.emartos.mediaconverter.rest.v1.utils.image;


import org.emartos.mediaconverter.rest.v1.utils.ServiceControllerValidationHelper;
import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;

public class ImageServiceControllerValidationHelper {
    private final String imageServiceControllerName;
    private static final Integer MIN_IMG_RESOLUTION = 1;
    private static final Integer MAX_IMG_RESOLUTION = 3840;

    public ImageServiceControllerValidationHelper(String imageServiceControllerName) {
        this.imageServiceControllerName = imageServiceControllerName;
    }

    public ImageServiceControllerValidationHelper checkValidResolutionRange(Integer value, String parameterName) throws BadRequestException {
        if (value < MIN_IMG_RESOLUTION || value > MAX_IMG_RESOLUTION) {
            throw new BadRequestException(String.format("%s field %s can't be less than %d and greater than %d", imageServiceControllerName,
                    parameterName, MIN_IMG_RESOLUTION, MAX_IMG_RESOLUTION));
        }
        return this;
    }

    public ImageServiceControllerValidationHelper checkValidImage(byte[] array, String parameterName) throws BadRequestException {
        ServiceControllerValidationHelper.checkNotNull(array, parameterName, imageServiceControllerName);
        ServiceControllerValidationHelper.checkNotEmpty(array, parameterName, imageServiceControllerName);
        if(!ImageType.isImage(array)) {
            throw new BadRequestException(String.format("%s field %s is not a valid image", imageServiceControllerName,
                    parameterName));
        }
        return this;
    }

}
