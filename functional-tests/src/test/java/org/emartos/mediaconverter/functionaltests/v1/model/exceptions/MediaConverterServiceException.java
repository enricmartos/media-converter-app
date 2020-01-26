package org.emartos.mediaconverter.functionaltests.v1.model.exceptions;

public abstract class MediaConverterServiceException extends Exception{

    public MediaConverterServiceException(String message) {
        super(message);
    }

    public MediaConverterServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
