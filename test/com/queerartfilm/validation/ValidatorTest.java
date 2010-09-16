/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.validation;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class ValidatorTest {

    Validator<String> mockV;
    String testMessage;

    @Before
    public void setUp() {
        testMessage = "Invalid";
        // will always return false and thus throw a ValidatorException
        mockV = new Validator<String>(
                new Predicate<String>() {

                    public boolean apply(String input) {
                        return Predicates.alwaysFalse().apply(input);
                    }
                }, testMessage);
    }

    @After
    public void tearDown() {
        mockV = null;
    }

    @Test
    public void testSetMessage() throws ValidatorException {
        String expected = testMessage;
        try {
            mockV.validate("");
        } catch (ValidatorException e) {
            String actual = e.getMessage();
            assertTrue(expected.equals(actual));
        }
    }

    @Test
    public void testExceptionMessage() throws Exception {
        try {
            mockV.validate("");
        } catch (ValidatorException e) {
            String msg = e.getMessage();
            System.out.println(msg);
            assertNotNull(msg);
            assertTrue(msg.toLowerCase().contains("invalid"));

        }
    }
}
