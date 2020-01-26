package org.emartos.mediaconverter.functionaltests.v1.model;

public class ResponseImage {
    private byte[] responseImageBytes;

    public ResponseImage(byte[] responseImageBytes) {
        this.responseImageBytes = responseImageBytes;
    }

    public byte[] getResponseImageBytes() {
        return this.responseImageBytes;
    }

    public void setResponseImageBytes(byte[] responseImageBytes) {
        this.responseImageBytes = responseImageBytes;
    }
}
