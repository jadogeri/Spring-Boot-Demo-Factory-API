package com.josephadogeridev.factory.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.exception.BadRequestException;

public class GlobalExceptionHandlerTest {
	@Test
	public void handleResourceNotFoundException() {
		GlobalExceptionHandler g = new GlobalExceptionHandler();
		ResourceNotFoundException ex = new ResourceNotFoundException("abc");
		ResponseEntity<Map<String, String>> actual = (ResponseEntity<Map<String, String>>) g.handleResourceNotFoundException(ex);

		      Assertions.assertTrue(actual.getBody().containsKey("message"));
		      Assertions.assertTrue(actual.getBody().get("code").equals("RESOURCE_NOT_FOUND"));
		      Assertions.assertTrue(actual.getBody().get("url").equals(""));
		      Assertions.assertTrue(actual.getBody().get("method").equals(""));
		      Assertions.assertTrue(actual.getBody().get("message").equals(ex.getMessage()));

	   }

	@Test
	public void handleIllegalArgumentException() {
		GlobalExceptionHandler g = new GlobalExceptionHandler();
		IllegalArgumentException ex = new IllegalArgumentException("abc");
		ResponseEntity<String> actual = g.handleIllegalArgumentException(ex);

		Assertions.assertTrue(actual instanceof ResponseEntity<String>);
		      Assertions.assertEquals(actual.getStatusCode(), HttpStatus.BAD_REQUEST);
		      Assertions.assertTrue(actual.getBody().contains("abc"));
		      Assertions.assertTrue(actual.getBody().equals("Invalid argument: " + ex.getMessage()));
	   }


	@Test
	public void handleConflictException() {
		GlobalExceptionHandler g = new GlobalExceptionHandler();
		ResourceConflictException ex = new ResourceConflictException("abc");
		ResponseEntity<Map<String, String>> expected = null;
		ResponseEntity<Map<String, String>> actual = g.handleConflictException(ex);

        Assertions.assertTrue(actual.getBody().containsKey("message"));
        Assertions.assertTrue(actual.getBody().get("code").equals("CONFLICT"));
        Assertions.assertTrue(actual.getBody().get("url").equals(""));
        Assertions.assertTrue(actual.getBody().get("method").equals(""));
        Assertions.assertTrue(actual.getBody().get("message").equals(ex.getMessage()));	}

	@Test
	public void handleBadRequestException() {
		GlobalExceptionHandler g = new GlobalExceptionHandler();
		BadRequestException ex = new BadRequestException("xyz");
		ResponseEntity<Map<String, String>> actual = g.handleBadRequestException(ex);

        Assertions.assertTrue(actual.getBody().containsKey("message"));
        Assertions.assertFalse(actual.getBody().get("message").equals("xyz"));
        Assertions.assertTrue(actual.getBody().get("code").equals("BAD_REQUEST"));
        Assertions.assertTrue(actual.getBody().get("url").equals(""));
        Assertions.assertTrue(actual.getBody().get("method").equals(""));
        Assertions.assertTrue(actual.getBody().get("message").equals(ex.getMessage()));
	}

	@Test
	public void handleGenericException() {
		GlobalExceptionHandler g = new GlobalExceptionHandler();
		Exception ex = new Exception("abc");
		ResponseEntity<Map<String, String>> actual = g.handleGenericException(ex);

        Assertions.assertTrue(actual.getBody().containsKey("message"));
        Assertions.assertTrue(actual.getBody().get("code").equals("INTERNAL_SERVER_ERROR"));
        Assertions.assertTrue(actual.getBody().get("url").equals(""));
        Assertions.assertTrue(actual.getBody().get("method").equals(""));
	}

	@Test
	public void handleNotFoundException() {
		GlobalExceptionHandler g = new GlobalExceptionHandler();
		NotFoundException ex = new NotFoundException("xyz");
		ResponseEntity<Map<String, String>> actual = g.handleNotFoundException(ex);

        Assertions.assertTrue(actual.getBody().containsKey("message"));
        Assertions.assertFalse(actual.getBody().get("message").equals("xyz"));
        Assertions.assertTrue(actual.getBody().get("code").equals("NOT_FOUND"));
        Assertions.assertTrue(actual.getBody().get("url").equals(""));
        Assertions.assertTrue(actual.getBody().get("method").equals(""));
        Assertions.assertTrue(actual.getBody().get("message").equals(ex.getMessage()));	}
}
