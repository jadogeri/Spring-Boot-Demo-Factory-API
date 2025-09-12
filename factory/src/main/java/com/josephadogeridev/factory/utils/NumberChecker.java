package com.josephadogeridev.factory.utils;

public class NumberChecker {
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            System.out.println("value is false");
            return false;
        }
        try {
            Integer.parseInt(str); // Or Integer.parseInt(str) for integers
            System.out.println("value is true");

            return true;
        } catch (NumberFormatException e) {
            System.out.println("value is catched");

            return false;
        }
    }
}