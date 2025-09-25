package com.josephadogeridev.factory.exceptions;

/**
 * @author Joseph Adogeri
 * @since 25-SEP-2025
 * @version 1.0.0
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceNotFoundExceptionTest {
	@Test
	public void ResourceNotFoundException() {
		String message = "abc";
		ResourceNotFoundException expected = new ResourceNotFoundException("abc");
		ResourceNotFoundException actual = new ResourceNotFoundException(message);

        assertInstanceOf(ResourceNotFoundException.class, expected);
        assertInstanceOf(ResourceNotFoundException.class, actual);
        assertEquals(actual.getMessage(), expected.getMessage());
        assertEquals(message, actual.getMessage());
    }
}
