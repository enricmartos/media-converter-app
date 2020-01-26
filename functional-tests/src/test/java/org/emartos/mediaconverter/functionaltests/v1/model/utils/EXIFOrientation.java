package org.emartos.mediaconverter.functionaltests.v1.model.utils;

import java.util.HashMap;
import java.util.Map;

public enum EXIFOrientation {

    AUTOROTATED(1),
    NINETY_DEGREES_LEFT(2),
    TWO_HUNDRED_SEVENTY_DEGREES_RIGHT(3),
    TWO_HUNDRED_SEVENTY_DEGREES_LEFT(4),
    ONE_HUNDRED_EIGHTY_DEGREES_LEFT(5),
    ONE_HUNDRED_EIGHTY_DEGREES_RIGHT(6),
    ZERO_DEGREES_LEFT(7),
    ZERO_DEGREES_RIGHT(8);

    private Integer orientation;
    private static final Map<Integer, EXIFOrientation> BY_LABEL = new HashMap<>();

    EXIFOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    public Integer getOrientation() {
        return orientation;
    }

    //Populate the BY_LABEL table on loading time
    static {
        for (EXIFOrientation orientation : values()) {
            BY_LABEL.put(orientation.getOrientation(), orientation);
        }
    }

    //This method can be used for reverse BY_LABEL purpose
    public static EXIFOrientation getExifOrientation(Integer orientation) {
        return BY_LABEL.get(orientation);
    }

}
