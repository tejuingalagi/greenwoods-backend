package com.teju.greenwoods.application.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.teju.greenwoods.application.dto.MediaResponse;
import com.teju.greenwoods.application.exception.InvalidRequestException;
import com.teju.greenwoods.domain.enums.MediaFolder;

@Service
public class MediaService {

    private static final long MAX_BYTES = 5L * 1024 * 1024;

    private final FileStorage fileStorage;
    private final Duration urlValidity;

    public MediaService(FileStorage fileStorage,
                        @Value("${app.media.url-expiry-minutes:60}") long expiryMinutes) {
        this.fileStorage = fileStorage;
        this.urlValidity = Duration.ofMinutes(expiryMinutes);
    }

    public MediaResponse uploadImage(byte[] content, MediaFolder folder) {
        if (content == null || content.length == 0) {
            throw new InvalidRequestException("The file is empty");
        }
        if (content.length > MAX_BYTES) {
            throw new InvalidRequestException("The file is too large (maximum 5 MB)");
        }

        String contentType = detectImageType(content);
        if (contentType == null) {
            throw new InvalidRequestException("Only JPEG, PNG or WebP images are allowed");
        }

        String key = folder.getPath() + "/" + UUID.randomUUID() + "." + extensionFor(contentType);
        fileStorage.put(key, content, contentType);

        return new MediaResponse(key, urlFor(key), contentType, content.length);
    }

    /** A temporary link for a stored image. Returns null when there is no key. */
    public String urlFor(String key) {
        if (key == null || key.isBlank()) {
            return null;
        }
        return fileStorage.presignedGetUrl(key, urlValidity);
    }

    public void delete(String key) {
        if (key != null && !key.isBlank()) {
            fileStorage.delete(key);
        }
    }

    // We look at the first bytes of the file itself, never at its name or the browser's claim.
    private static String detectImageType(byte[] b) {
        if (b.length >= 3
                && (b[0] & 0xFF) == 0xFF && (b[1] & 0xFF) == 0xD8 && (b[2] & 0xFF) == 0xFF) {
            return "image/jpeg";
        }
        if (b.length >= 8
                && (b[0] & 0xFF) == 0x89 && b[1] == 'P' && b[2] == 'N' && b[3] == 'G'
                && b[4] == 0x0D && b[5] == 0x0A && b[6] == 0x1A && b[7] == 0x0A) {
            return "image/png";
        }
        if (b.length >= 12
                && b[0] == 'R' && b[1] == 'I' && b[2] == 'F' && b[3] == 'F'
                && b[8] == 'W' && b[9] == 'E' && b[10] == 'B' && b[11] == 'P') {
            return "image/webp";
        }
        return null;
    }

    private static String extensionFor(String contentType) {
        return switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            default -> "webp";
        };
    }
}