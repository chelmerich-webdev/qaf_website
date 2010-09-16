package com.queerartfilm.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ch67dotnet
 */
public class UtilsTest {

    @Test
    public void testCreateID() {
        // Test Alpha
        testID("The Prime of Miss Jean Brody", "prime-of-miss-jean-brody");

        // Test digits
        testID("1 2 3 4 56 789 0", "1-2-3-4-56-789-0");

        //Punctuation
        testID("No Punctuation ., <> !@#$%^&*;:'\" {}[] ¿?", "no-punctuation");

        // Hyphens
        testID("----------", "");
        testID("Z----------A", "z-a");

        // Empty String
        testID("", "");

        // Leading and trailing hyphens
        testID("- X Y Z -", "x-y-z");

    }

    private void testID(String title, String expected) {
        String actual = Utils.createID(title);
        assertEquals(actual, expected, actual);
        // test that ID itself remains unchanged when passed in as a argument
        assertEquals(actual, Utils.createID(title));


    }
}
