package com.queerartfilm.validation;

import com.google.common.base.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsDatePTest {

    String format = "yyyy-mm-dd hh:mm:ss a z";
    SimpleDateFormat df;
    Predicate<String> p;
    @Before
    public void setUp() {
        df = new SimpleDateFormat(format);
        p = new IsDateP(format);

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testOverridenApply_6args() throws ParseException {
//        IsDateP.dfDay.format(IsDateP.dfDay.parse("2")),
        assertTrue(new IsDateP().apply("February", "29", "2096", "8", "23", "AM"));
    }

    @Test
    public void testApply() throws ParseException {
        String dateString = "1970-01-01 12:00:00 PM GMT";
        assertTrue(p.apply(dateString));
        System.out.println("Date long: " + df.parse(dateString).getTime());
        dateString = dateString.substring(0, dateString.length()-2);
        assertFalse(p.apply(dateString));
    }

    @Test
    public void testDateFormats() throws ParseException {
        String fmt = "yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        String dateString = "1970";
        IsDateP p2 = new IsDateP(fmt);
        assertTrue(p2.apply(dateString));

        System.out.println("Date long: " + sdf.parse(dateString).getTime());
        dateString = dateString.substring(0, dateString.length()-2);
        assertFalse(p.apply(dateString));
    }


}