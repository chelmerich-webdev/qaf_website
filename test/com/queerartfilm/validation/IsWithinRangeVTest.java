/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.validation;

import com.queerartfilm.validation.IsWithinRangeV;
import com.queerartfilm.validation.ValidatorException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsWithinRangeVTest {

    IsWithinRangeV vInclusive, vExclusive;
    int min, max;
    boolean inclusiveMin, inclusiveMax;

    @Before
    public void setUp() {
        min = 5;
        max = 50;
        inclusiveMin = false;
        inclusiveMax = false;
        vInclusive = new IsWithinRangeV(min, max);
        vExclusive = new IsWithinRangeV(min, max, inclusiveMin, inclusiveMax);
    }

    @After
    public void tearDown() {
        vInclusive = null;
        vExclusive = null;

    }

    @Test
    public void testValidateInclusivePasses() throws ValidatorException {
        for (int i = min; i <= max; i++) {
            vInclusive.validate(String.valueOf(i));

        }
    }
    @Test
    public void testValidateExclusivePasses() throws ValidatorException {
        for (int i = min+1; i < max; i++) {
            vExclusive.validate(String.valueOf(i));
        }
    }

    @Test(expected=ValidatorException.class)
    public void testValidateExlusiveFailsAtMin() throws ValidatorException {
        vExclusive.validate(String.valueOf(min));
        fail();
    }
    
    @Test(expected=ValidatorException.class)
    public void testValidateExlusiveFailsAtMax() throws ValidatorException {
        vExclusive.validate(String.valueOf(max));
        fail();
    }

    @Test(expected=ValidatorException.class)
    public void testValidateFailsOnNull() throws ValidatorException {
        vInclusive.validate(null);
        fail();
    }

}