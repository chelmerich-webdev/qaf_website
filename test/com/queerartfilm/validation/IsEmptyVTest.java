package com.queerartfilm.validation;

import com.queerartfilm.validation.IsEmptyV;
import com.queerartfilm.validation.ValidatorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ch67dotnet
 */
public class IsEmptyVTest {

    IsEmptyV v;

    @Before
    public void setUp() {
        v = new IsEmptyV();
    }

    @After
    public void tearDown() {
        v = null;
    }



    @Test
    public void testIsEmptyVTest() {
        try {
            v.validate("");
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test(expected = ValidatorException.class)
    public void testNotEmpty() throws ValidatorException {

        v.validate("not empty");
    }

    @Test(expected = ValidatorException.class)
    public void testNull() throws ValidatorException {

        v.validate(null);
    }


}
