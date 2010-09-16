/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.film;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ch67dotnet
 */
public class RatingsTest {

    public RatingsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }


    @Test
    public void testValueOf() {
        for (Rating r : Rating.values()) {
            try {
                Rating.valueOf(r.name());
            } catch (IllegalArgumentException ex) {
                fail("Not an enum value: " + r.toString());
            }
        }
    }

    @Ignore
    public void testGetOptionTag() {
        for (Rating r : Rating.values()) {
            assertTrue(r.toString().contains("option"));

        }

    }

    @Ignore
    public void testGetOptionSelectedTag() {
        System.out.println("getOptionSelectedTag");
        Rating instance = null;
        String expResult = "";
//        String result = instance.getOptionSelectedTag();
//        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}
