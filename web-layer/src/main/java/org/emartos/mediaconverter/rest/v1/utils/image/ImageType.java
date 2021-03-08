package org.emartos.mediaconverter.rest.v1.utils.image;

import java.util.Arrays;

public enum ImageType {

    JPG(new byte[]{-1, -40, -1}),
    BMP(new byte[]{66, 77}),
    PNG(new byte[]{-119, 80, 78, 71, 13, 10, 26, 10});

    private byte[] header;

    ImageType(byte[] header) {
        this.header = header;
    }

    static boolean isImage(byte [] content) {
        byte[] imageHeader = new byte[] {content[0], content[1], content[2] };
        for(ImageType imageType : ImageType.values()) {
            if(Arrays.equals(imageHeader, imageType.header)){
                return true;
            }
        }
        return false;
    }

}
