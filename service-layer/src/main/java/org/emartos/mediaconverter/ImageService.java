package org.emartos.mediaconverter;

//@Service
public interface ImageService {

    byte[] resizeImage(byte[] image, Integer width, Integer height);

    byte[] autorotateImage(byte[] image);
}
