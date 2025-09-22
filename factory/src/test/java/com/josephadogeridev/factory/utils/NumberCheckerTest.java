package com.josephadogeridev.factory.utils;

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
