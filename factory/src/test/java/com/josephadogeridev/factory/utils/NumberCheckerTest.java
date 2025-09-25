package com.josephadogeridev.factory.utils;

/**
 * @author Joseph Adogeri
 * @since 25-SEP-2025
 * @version 1.0.0
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class NumberCheckerTest {
	@Test
	public void isNumeric() {
		String str = "10";
		boolean expected = true;
		boolean actual = NumberChecker.isNumeric(str);

		assertEquals(expected, actual);
	}
}
