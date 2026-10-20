package com.teju.greenwoods.domain.enums;

public enum MediaFolder {
    GALLERY("gallery"),
    HERO("hero"),
    AMENITIES("amenities"),
    FLOOR_PLANS("floor-plans"),
    SITE("site");

    private final String path;

    MediaFolder(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}