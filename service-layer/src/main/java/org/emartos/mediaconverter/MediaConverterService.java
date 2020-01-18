package org.emartos.mediaconverter;

//@Service
public interface MediaConverterService {

    byte[] resizeImage(byte[] image, Integer width, Integer height);

    byte[] autorotateImage(byte[] image);
}
