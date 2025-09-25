package com.josephadogeridev.factory.utils;

/**
 * @author Joseph Adogeri
 * @since 25-SEP-2025
 * @version 1.0.0
 */

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RequestContextUtilTest {
	@Test
	public void getCurrentHttpRequest() {
		HttpServletRequest expected = null;
		HttpServletRequest actual = RequestContextUtil.getCurrentHttpRequest();

		assertEquals(expected, actual);
	}
}
