/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.validation;

import com.queerartfilm.validation.IsNullV;
import com.queerartfilm.validation.Validator;
import com.queerartfilm.validation.ValidatorException;
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
public class IsNullVTest {

    Validator<Object> v;
    Validator<String> vString;
    Validator<Number> vNumber;

    @Before
    public void setUp() {

        v = new IsNullV<Object>();
        vString = new IsNullV<String>();
        vNumber = new IsNullV<Number>();
    }

    @After
    public void tearDown() {
        v = null;
        vString = null;
        vNumber = null;
    }


    @Test
    public void testValidateNull() throws ValidatorException {

        try {
            v.validate(null);
            vString.validate(null);
            vNumber.validate(null);

        } catch (ValidatorException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    @Test(expected=ValidatorException.class)
    public void testValidateAnObject() throws ValidatorException {
        v.validate(new Object());
        fail();

    }

    @Test(expected=ValidatorException.class)
    public void testValidateAString() throws ValidatorException {
        vString.validate("");
        fail();

    }

    @Test(expected=ValidatorException.class)
    public void testValidateANumber() throws ValidatorException {
        vNumber.validate(new Integer(0));
        fail();

    }

}
