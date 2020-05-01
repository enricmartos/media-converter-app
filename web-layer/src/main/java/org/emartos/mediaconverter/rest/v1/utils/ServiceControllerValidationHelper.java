package org.emartos.mediaconverter.rest.v1.utils;

import org.emartos.mediaconverterapi.v1.exceptions.BadRequestException;

public abstract class ServiceControllerValidationHelper {

    public static void checkNotNull(Object value, String parameterName, String serviceControllerName) throws BadRequestException {
        if (value == null) {
            throw new BadRequestException(String.format("%s field %s can't be null", serviceControllerName, parameterName));
        }
    }

    public static void checkNotEmpty(byte[] array, String parameterName, String serviceControllerName) throws BadRequestException {
        if (array.length == 0) {
            throw new BadRequestException(String.format("%s field %s can't be null", serviceControllerName, parameterName));
        }
    }


}
