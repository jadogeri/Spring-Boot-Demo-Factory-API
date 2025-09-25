package com.josephadogeridev.factory.exceptions;

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
