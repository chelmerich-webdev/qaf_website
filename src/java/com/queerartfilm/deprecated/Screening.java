package com.queerartfilm.deprecated;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.queerartfilm.event.deprecated.Event;
import com.queerartfilm.film.Venue;
import com.queerartfilm.validation.IsDateP;
import com.queerartfilm.validation.IsNotEmptyOrNullV;
import java.util.Date;
import javax.persistence.Id;

/**
 * Java Bean that models a QAF series film selection screening date, time, and venue.
 * Annotated for Objectify / GAE persistence.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Cached
public class Screening implements Comparable<Screening> {

//    static final String dateString = "M/dd/yyyy hh:mm a";
//    static String dateOnForm = "yyyy-M-dd hh:mm a";
//    static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateString);


    @Id
    private long id; // id is the date and time represented as a long value
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String ampm;
    private Date showDateTime;
    private Venue venue;
    private Key<Event> parentKey;

    public Screening(Date showDateTime, Venue venue) {
        this.setShowDateTime(showDateTime);
        this.setVenue(venue);
    }

    Screening() {
        this(new Date(),Venue.IFC_CENTER);
    }

    private void setId() {
        this.id = showDateTime.getTime();
    }

    public long getId() {
        return id;
    }

    /**
     * Returns a defensive copy of the <code>Date</code> value.
     * @return a defensive copy of the <code>Date</code> value.
     */
    public Date getShowDateTime() {
        return new Date(showDateTime.getTime());
    }

    public void setShowDateTime(Date showDateTime) {
        this.showDateTime = showDateTime;
        // set id value here as it is dependent on this showDateAndTime
        this.setId();
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    

    public Key<Event> getParentKey() {
        return parentKey;
    }

    public void setParentKey(Key<Event> parentKey) {
        this.parentKey = parentKey;
    }

    public boolean isAssigned() {
        return IsNotEmptyOrNullV.P().apply(this.parentKey.getName());
    }

    public int compareTo(Screening that) {
        return this.showDateTime.compareTo(that.showDateTime);
    }


    @Override
    public String toString() {
        return IsDateP.defaultDateFormat.format(showDateTime) + " at " + this.venue.toString();
    }
    /**
     * Overrides <code>Object#equals</code> and defines equality of two
     * <code>Screening</code>s on <code>showDateAndTime</code>
     * and <code>venue</code> values only.
     *
     * @param obj
     * @return <code>true</code> if equal, <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Screening other = (Screening) obj;
        if (this.showDateTime != other.showDateTime && (this.showDateTime == null || !this.showDateTime.equals(other.showDateTime))) {
            return false;
        }
        if ((this.venue == null) ? (other.venue != null) : !this.venue.equals(other.venue)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.showDateTime != null ? this.showDateTime.hashCode() : 0);
        hash = 29 * hash + (this.venue != null ? this.venue.hashCode() : 0);
        return hash;
    }
}
