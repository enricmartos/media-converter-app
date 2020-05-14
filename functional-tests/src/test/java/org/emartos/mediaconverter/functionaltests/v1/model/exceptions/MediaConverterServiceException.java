package org.emartos.mediaconverter.functionaltests.v1.model.exceptions;

abstract class MediaConverterServiceException extends Exception{

    MediaConverterServiceException(String message) {
        super(message);
    }

    MediaConverterServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
