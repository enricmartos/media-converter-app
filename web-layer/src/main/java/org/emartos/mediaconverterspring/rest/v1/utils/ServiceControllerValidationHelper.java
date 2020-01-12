package org.emartos.mediaconverterspring.rest.v1.utils;


import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;

public class ServiceControllerValidationHelper {
    private final String serviceControllerName;
    private static final Integer MIN_IMG_RESOLUTION = 1;
    private static final Integer MAX_IMG_RESOLUTION = 3840;

    public ServiceControllerValidationHelper(String serviceControllerName) {
        this.serviceControllerName = serviceControllerName;
    }

    public ServiceControllerValidationHelper checkNotNull(Object value, String parameterName) throws BadRequestException {
        if (value == null) {
            throw new BadRequestException(String.format("%s field %s can't be null", serviceControllerName, parameterName));
        }
        return this;
    }

    private ServiceControllerValidationHelper checkNotEmpty(byte[] array, String parameterName) throws BadRequestException {
        if (array.length == 0) {
            throw new BadRequestException(String.format("%s field %s can't be null", serviceControllerName, parameterName));
        }
        return this;
    }

    public ServiceControllerValidationHelper checkValidRange(Integer value, String parameterName) throws BadRequestException {
        if (value < MIN_IMG_RESOLUTION || value > MAX_IMG_RESOLUTION) {
            throw new BadRequestException(String.format("%s field %s can't be less than %d and greater than %d", serviceControllerName,
                    parameterName, MIN_IMG_RESOLUTION, MAX_IMG_RESOLUTION));
        }
        return this;
    }

    public ServiceControllerValidationHelper checkValidImage(byte[] array, String parameterName) throws BadRequestException {
        checkNotNull(array, parameterName);
        checkNotEmpty(array, parameterName);
        if(!ImageType.isImage(array)) {
            throw new BadRequestException(String.format("%s field %s is not a valid image", serviceControllerName,
                    parameterName));
        }
        return this;
    }

}
