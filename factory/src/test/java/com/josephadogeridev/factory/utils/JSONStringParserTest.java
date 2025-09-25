package com.josephadogeridev.factory.utils;

/**
 * @author Joseph Adogeri
 * @since 25-SEP-2025
 * @version 1.0.0
 */

import org.junit.jupiter.api.*;

public class JSONStringParserTest {
	@Test
	public void extract() throws Exception {
		JSONStringParser j = new JSONStringParser();
		String jsonString = "{\"message\":\"Jane Smith\",\"age\":25}";
		j.extract(jsonString);
	}
}
