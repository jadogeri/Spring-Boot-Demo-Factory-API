package com.josephadogeridev.factory.utils;

import org.junit.jupiter.api.*;

public class JSONStringParserTest {
	@Test
	public void extract() throws Exception {
		JSONStringParser j = new JSONStringParser();
		String jsonString = "{\"message\":\"Jane Smith\",\"age\":25}";
		j.extract(jsonString);
	}
}
