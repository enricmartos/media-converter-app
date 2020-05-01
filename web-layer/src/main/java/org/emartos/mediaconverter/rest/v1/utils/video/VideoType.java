package org.emartos.mediaconverter.rest.v1.utils.video;

public enum VideoType {
    // mp4
    // hollander
    // 0, 0, 0, 24, 102, 116, 121, 112, 109, 112, 52, 50, 0, 0, 0,
    // btravel
    // 0, 0, 0, 24, 102, 116, 121, 112, 109, 112, 52, 50, 0, 0, 0
    // mov
    // caixa
    // 0, 0, 0, 20, 102, 116, 121, 112, 113, 116, 32, 32, 0, 0, 0,
    MP4(new byte[]{0, 0, 0});

    private byte[] header;

    VideoType(byte[] header) {
        this.header = header;
    }

    static boolean isVideo(byte [] content) {
        for(VideoType videoType: VideoType.values()) {
            if(sameFirstOctects(content, videoType.header)){
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
