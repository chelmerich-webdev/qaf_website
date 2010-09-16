/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.deprecated;

import com.google.common.base.Predicate;
import com.queerartfilm.film.Venue;
import com.queerartfilm.validation.IsDateP;
import com.queerartfilm.validation.IsIntegerV;
import com.queerartfilm.validation.IsNotEmptyOrNullV;
import com.queerartfilm.validation.IsWithinRangeV;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public enum Field {

    YEAR("year", "2010", IsIntegerV.P) {

        @Override
        public void setScreeningField(Screening screening, String value) {
            screening.setYear(value);
        }
    },
    MONTH("month", "January", IsNotEmptyOrNullV.P()) {

        @Override
        public void setScreeningField(Screening screening, String value) {
            screening.setMonth(value);
        }
    },
    DAY("day", "01", IsWithinRangeV.P(1, 31, true, true)) {

        @Override
        public void setScreeningField(Screening screening, String value) {
            screening.setDay(value);
        }
    },
    HOUR("hour", "12", IsWithinRangeV.P(0, 12, true, true)) {

        @Override
        public void setScreeningField(Screening screening, String value) {
            screening.setHour(value);
        }
    },
    MINUTE("minute", "00", IsWithinRangeV.P(0, 60, true, false)) {

        @Override
        public void setScreeningField(Screening screening, String value) {
            screening.setMinute(value);
        }
    },
    AMPM("ampm", "PM", IsNotEmptyOrNullV.P()) {

        @Override
        public void setScreeningField(Screening s, String value) {
            s.setAmpm(value);
        }
    },
    SHOW_DATE_TIME("showDateTime", "1/1/2010 12:00 PM", new IsDateP()) {

        @Override
        public void setScreeningField(Screening s, String value) {
            try {
                s.setShowDateTime(IsDateP.defaultDateFormat.parse(value));
            } catch (ParseException ex) {
                Logger.getLogger(Field.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    },
    VENUE("venue", Venue.values()[0].name(), IsNotEmptyOrNullV.P()) {

        @Override
        public void setScreeningField(Screening s, String value) {
            if (this.validate(value)) {
                try {
                    s.setVenue(Venue.valueOf(value));
                } catch (IllegalArgumentException ex) {
                    logger.warning("Bad Venue enum string, value:  " + ex.getMessage());
                }
            }
        }
    },;
    private static Logger logger = Logger.getLogger(Field.class.getName());
    private String fieldname;
    private String defaultValue;
    private Predicate<String> validator;

    private Field(String fieldname, String defaultValue, Predicate<String> validator) {
        this.fieldname = fieldname;
        this.defaultValue = defaultValue;
        this.validator = validator;
    }

    public String getFieldname() {
        return fieldname;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean validate(String input) {
        return validator.apply(input);
    }

    public abstract void setScreeningField(Screening screening, String value);
}
