/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.validation;

import com.queerartfilm.validation.ValidatorException;
import com.queerartfilm.validation.IsLessThanV;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsLessThanVTest {


    IsLessThanV v;
    int max;
    @Before
    public void setUp() {
        max = 100;
        v = new IsLessThanV(max);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testValidationPasses() throws ValidatorException {
        for (int i = -max; i < (max-1); i++) {
            v.validate(String.valueOf(i));
        }
    }

    @Test(expected=ValidatorException.class)
    public void testValidationFailsAtMax() throws ValidatorException {
        v.validate(Integer.toString(max));
        fail();
    }
    @Test(expected=ValidatorException.class)
    public void testValidationFailsAtNonDigit() throws ValidatorException {
        v.validate("abc");
        fail();
    }

}