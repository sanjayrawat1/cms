package com.github.sanjayrawat1.cms.enumeration;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Sanjay Singh Rawat
 */
public enum MediaType {
    JPEG("image", "jpeg", "image/jpeg"),
    PNG("image", "png", "image/png"),
    MP4("video", "mp4", "video/mp4"),
    UNKNOWN("unknown", "unknown", "Only JPEG, PNG and MP4 file is allowed");

    private static final Map<String, MediaType> SUB_TYPE_TO_MEDIA_TYPE = Arrays
        .stream(values())
        .filter(mediaType -> mediaType != UNKNOWN)
        .collect(Collectors.toMap(MediaType::getSubType, Function.identity()));

    private static final Map<String, MediaType> VALUE_TO_MEDIA_TYPE = Arrays
        .stream(values())
        .filter(mediaType -> mediaType != UNKNOWN)
        .collect(Collectors.toMap(MediaType::getValue, Function.identity()));

    private final String type;
    private final String subType;
    private final String value;

    MediaType(String type, String subType, String value) {
        this.type = type;
        this.subType = subType;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getValue() {
        return value;
    }

    public static MediaType getByValue(String value) {
        return VALUE_TO_MEDIA_TYPE.getOrDefault(value, UNKNOWN);
    }

    public static MediaType getBySubType(String subType) {
        return SUB_TYPE_TO_MEDIA_TYPE.getOrDefault(subType, UNKNOWN);
    }
}
