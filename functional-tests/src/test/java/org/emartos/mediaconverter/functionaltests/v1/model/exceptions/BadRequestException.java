package org.emartos.mediaconverter.functionaltests.v1.model.exceptions;

import org.emartos.mediaconverter.functionaltests.v1.model.exceptions.MediaConverterServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends MediaConverterServiceException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable t) {
        super(message, t);
    }
}
