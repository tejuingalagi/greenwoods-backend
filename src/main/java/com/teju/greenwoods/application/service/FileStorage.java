package com.teju.greenwoods.application.service;

import java.time.Duration;

public interface FileStorage {

    void put(String key, byte[] content, String contentType);

    void delete(String key);

    String presignedGetUrl(String key, Duration validFor);
}