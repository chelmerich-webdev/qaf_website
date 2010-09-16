/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.validation;

import com.queerartfilm.validation.ValidatorException;
import com.queerartfilm.validation.IsIntegerV;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsIntegerVTest {

    IsIntegerV v;

    @Before
    public void setUp() {
        v = new IsIntegerV();

    }

    @After
    public void tearDown() {
        v = null;

    }

    @Test
    public void testValidatePasses() throws ValidatorException {

        try {
            String digits = "0000123";
            v.validate(digits);
            digits = "-123";
            v.validate(digits);
            digits = "0";
            v.validate(digits);
            digits = "00";
            v.validate(digits);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Test(expected=ValidatorException.class)
    public void testValidateFailsOnNonDigit() throws ValidatorException {
        v.validate("abc");
        fail();
    }
    @Test(expected=ValidatorException.class)
    public void testValidateFailsOnDoubleHyphen() throws ValidatorException {
        v.validate("--123");
        fail();
    }
    @Test(expected=ValidatorException.class)
    public void testValidateFailsOnEmpty() throws ValidatorException {
        v.validate("");
        fail();
    }
    @Test(expected=ValidatorException.class)
    public void testValidateFailsOnHyphen() throws ValidatorException {
        v.validate("-");
        fail();
    }
    @Test(expected=ValidatorException.class)
    public void testValidateFailsOnSpace() throws ValidatorException {
        v.validate("12345 67890");
        fail();
    }
    @Test(expected=ValidatorException.class)
    public void testValidateFailsOnNull() throws ValidatorException {
        v.validate(null);
        fail();
    }


}