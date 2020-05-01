package org.emartos.mediaconverter;


public interface MediaConverterVideoService {

    byte[] trimVideo(byte[] video, Integer startMinute, Integer startSecond,
                                     Integer endMinute, Integer endSecond);

}
