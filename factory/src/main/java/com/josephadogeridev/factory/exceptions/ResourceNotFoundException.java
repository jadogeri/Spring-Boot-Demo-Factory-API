package com.josephadogeridev.factory.exceptions;

/**
 * @author Joseph Adogeri
 * @since 25-SEP-2025
 * @version 1.0.0
 */

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
