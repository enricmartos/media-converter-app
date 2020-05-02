package org.emartos.mediaconverter;


public interface VideoService {

    byte[] trimVideo(byte[] video, Integer startMinute, Integer startSecond,
                                     Integer endMinute, Integer endSecond);

}
