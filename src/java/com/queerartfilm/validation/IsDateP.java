/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.validation;

import com.google.common.base.Predicate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsDateP implements Predicate<String> {

    // date LONG time SHORT = MMMM d, yyyy h:mm a
    public static String defaultStringFormat = ("%s %s, %s %s:%s %s");
    public static DateFormat defaultDateFormat =
            DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, new Locale("en", "US"));
    public static DateFormat dfYear = new SimpleDateFormat("yyyy");
    public static DateFormat dfMonth = new SimpleDateFormat("MMMM");
    public static DateFormat dfDay = new SimpleDateFormat("d"); // no zero-padding
    public static DateFormat dfHour = new SimpleDateFormat("h"); // no zero-padding
    public static DateFormat dfMinute = new SimpleDateFormat("mm");
    public static DateFormat dfAmpm = new SimpleDateFormat("a");
    private DateFormat df;
    public IsDateP(String format) {
        this(new SimpleDateFormat(format));
    }

    public IsDateP() {
        this.df = defaultDateFormat;
    }

    public IsDateP(DateFormat df) {
        this.df = df;
    }

    public boolean apply(String month, String day, String year, String hour, String minute, String ampm) {
        String dateString = String.format(
                defaultStringFormat, month, day, year, hour, minute, ampm);
        Date parsedDate = null;
        try {
            parsedDate = df.parse(dateString);
        } catch (ParseException ex) {
            return false;
        }

        if (dfYear.format(parsedDate).equals(year) &&
            dfMonth.format(parsedDate).equals(month) &&
            dfDay.format(parsedDate).equals(day) &&
            dfHour.format(parsedDate).equals(hour) &&
            dfMinute.format(parsedDate).equals(minute) &&
            dfAmpm.format(parsedDate).equals(ampm)){
            return true;
        } else {
            return false;
        }
    }

    public boolean apply(String input) {
        try {
            df.parse(input);
            return true;
//        } catch (ParseException ex) {
        } catch (Exception ex) {
            return false;
        }
    }



}
