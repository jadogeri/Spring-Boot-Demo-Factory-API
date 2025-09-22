package com.josephadogeridev.factory.exceptions;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorResponseTest {
	@Test
	public void getMessage() {
		ErrorResponse e = new ErrorResponse("abc", 123);
		String expected = "abc";
		String actual = e.getMessage();

		assertEquals(expected, actual);
	}

	@Test
	public void getStatus() {
		ErrorResponse e = new ErrorResponse("abc", 123);
		int expected = 123;
		int actual = e.getStatus();

		assertEquals(expected, actual);
	}

	@Test
	public void getTimestamp() {
		ErrorResponse e = new ErrorResponse("abc", 123);
		long expected = 0L;
		long actual = e.getTimestamp();
        assertTrue(expected < actual);


	}
}
