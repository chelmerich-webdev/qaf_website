package com.queerartfilm.event.deprecated;

import com.queerartfilm.event.deprecated.Event;
import java.text.ParseException;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class EventTest {

    int len = 3;
    Event[] events = new Event[len];
    String[] months = {"January", "July", "November"};
    String[] years = {"1967", "1998", "2010"};

    @Before
    public void setUp() {
        for (int i = 0; i < 3; i++) {
            events[i] = new Event();
            events[i].setMonth(months[i]);
            events[i].setYear(years[i]);
            String dateString = String.format(
                    Event.ID_STRING_FORMAT, events[i].getMonth(), events[i].getYear());
            long id = 0L;
            try {
                id = Event.ID_DATE_FORMATTER.parse(dateString).getTime();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            events[i].setId(id);
        }

    }

    @After
    public void tearDown() {
    }


    @Test
    public void testCompareToNull() {
        assertTrue(1 == events[0].compareTo(null));
    }
    @Test
    public void testCompareTo() {
        boolean greaterTest;
        boolean lessTest;
        boolean equalsTest;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                greaterTest = i > j;
                lessTest = i < j;
                equalsTest = i == j;
                if (greaterTest) {
                    assertTrue(1 == events[i].compareTo(events[j]));
                } else {
                    assertFalse(1 == events[i].compareTo(events[j]));
                }
                if (lessTest) {
                    assertTrue(-1 == events[i].compareTo(events[j]));
                } else {
                    assertFalse(-1 == events[i].compareTo(events[j]));
                }
                if (equalsTest) {
                    assertTrue(0 == events[i].compareTo(events[j]));
                } else {
                    assertFalse(0 == events[i].compareTo(events[j]));
                }
            }
        }
    }
    @Test
    public void testEquals() {
        Event event = new Event();
        for (int i = 0; i < len; i++) {
            String dateString = String.format(
                    Event.ID_STRING_FORMAT, months[i], years[i]);
            long id = 0L;
            try {
                id = Event.ID_DATE_FORMATTER.parse(dateString).getTime();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            event.setId(id);
            assertEquals(event, events[i]);

        }
    }

    @Test
    public void testGetYear() {
        for (int i = 0; i < len; i++) {
            assertEquals(years[i], events[i].getYear());
        }
    }

    @Test
    public void testGetMonth() {
        for (int i = 0; i < len; i++) {
            assertEquals(months[i], events[i].getMonth());
        }
    }

}
