package org.emartos.mediaconverter.rest.v1.utils;

enum ImageType {

    JPG(new byte[]{-1, -40, -1}),
    BMP(new byte[]{66, 77}),
    PNG(new byte[]{-119, 80, 78, 71, 13, 10, 26, 10});

    private byte[] header;

    ImageType(byte[] header) {
        this.header = header;
    }

    static boolean isImage(byte [] content) {
        for(ImageType imageType : ImageType.values()) {
            if(sameFirstOctects(content, imageType.header)){
                return true;
            }
        }
        return false;
    }

    private static boolean sameFirstOctects(byte[] arrayOriginal, byte[] arrayTarget) {
        for (int i = 0; i < arrayTarget.length; i++) {
            if (arrayOriginal[i] != arrayTarget[i]) {
                return false;
            }
        }
        return true;
    }

}
