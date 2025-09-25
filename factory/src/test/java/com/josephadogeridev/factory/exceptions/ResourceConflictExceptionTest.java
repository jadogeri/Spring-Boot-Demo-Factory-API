package com.josephadogeridev.factory.exceptions;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceConflictExceptionTest {
	@Test
	public void ResourceConflictException() {
		String message = "abc";
		ResourceConflictException expected = new ResourceConflictException("abc");
		ResourceConflictException actual = new ResourceConflictException(message);

        assertInstanceOf(ResourceConflictException.class, expected);
        assertInstanceOf(ResourceConflictException.class, actual);
        assertEquals(actual.getMessage(), expected.getMessage());
        assertEquals(message, actual.getMessage());
	}
}
