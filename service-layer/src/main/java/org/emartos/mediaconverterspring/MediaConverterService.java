package org.emartos.mediaconverterspring;

import org.springframework.stereotype.Service;

//@Service
public interface MediaConverterService {

    byte[] resizeImage(byte[] image, Integer width, Integer height);

    byte[] autorotateImage(byte[] image);
}
