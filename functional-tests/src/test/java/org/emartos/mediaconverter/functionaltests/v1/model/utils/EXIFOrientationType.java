package org.emartos.mediaconverter.functionaltests.v1.model.utils;

public enum EXIFOrientationType {

    AUTOROTATED(1),
    NINETY_DEGREES_LEFT(2),
    TWO_HUNDRED_SEVENTY_DEGREES_RIGHT(3),
    TWO_HUNDRED_SEVENTY_DEGREES_LEFT(4),
    ONE_HUNDRED_EIGHTY_DEGREES_LEFT(5),
    ONE_HUNDRED_EIGHTY_DEGREES_RIGHT(6),
    ZERO_DEGREES_LEFT(7),
    ZERO_DEGREES_RIGHT(8),
    UNKNOWN(-1);

    private Integer type;

    EXIFOrientationType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static EXIFOrientationType getByType(Integer type) {
        for (EXIFOrientationType exifOrientationType: values()) {
            if (exifOrientationType.getType().equals(type)) {
                return exifOrientationType;
            }
        }
        return UNKNOWN;
    }

}
