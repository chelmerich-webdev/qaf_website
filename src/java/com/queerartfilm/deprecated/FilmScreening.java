package com.queerartfilm.deprecated;

import com.google.appengine.api.datastore.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Java bean that models a QAF film screening, and now replaced by <code>QafSeriesEvent</code>.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Deprecated
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class FilmScreening implements Comparable<FilmScreening>{

    private static final Logger logger = Logger.getLogger(FilmScreening.class.getName());
//    FilmScreening(String title, Date screeningDateAndTime) {
//        this.title = title;
//        this.screeningDateAndTime = screeningDateAndTime;
//
//    }

    FilmScreening() {
    }

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    Key key;

    @Persistent
    private String title; //*
    @Persistent
    private int year; //*
    @Persistent
    private String directorFirst;
    @Persistent
    private String directorLast; //*
    @Persistent
    private String synopsis;
    @Persistent
    private String series;
    @Persistent
    private Date screeningDateAndTime; //*
    @Persistent
    private String screeningVenue;
    @Persistent
    private String venueAddress1;
    @Persistent
    private String venueAddress2;
    @Persistent
    private String venuePhoneNumber;
    @Persistent
    private String presenterFirst;
    @Persistent
    private String presenterLast;



    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getDirectorFirst() {
        return directorFirst;
    }

    public void setDirectorFirst(String directorFirst) {
        this.directorFirst = directorFirst;
    }

    public String getDirectorLast() {
        return directorLast;
    }

    public void setDirectorLast(String directorLast) {
        this.directorLast = directorLast;
    }

    public String getPresenterFirst() {
        return presenterFirst;
    }

    public void setPresenterFirst(String presenterFirst) {
        this.presenterFirst = presenterFirst;
    }

    public String getPresenterLast() {
        return presenterLast;
    }

    public void setPresenterLast(String presenterLast) {
        this.presenterLast = presenterLast;
    }

    public Date getScreeningDateAndTime() {
        return new Date(screeningDateAndTime.getTime());
    }

    public void setScreeningDateAndTime(Date screeningDateAndTime) {
        this.screeningDateAndTime = screeningDateAndTime;
    }

    public void setScreeningDateAndTime(int year, int month, int date, int hour) {
        Calendar screenDate = Calendar.getInstance();
        screenDate.set(year, month, date, hour, 0);
        screenDate.set(Calendar.SECOND, 0);
        screenDate.set(Calendar.AM_PM, Calendar.PM);
        setScreeningDateAndTime(screenDate.getTime());
    }

    public void setScreeningDateAndTime(String... args) {
        try {
            int[] nums = new int[args.length];
            for (int i = 0; i < args.length; i++) {
                nums[i] = Integer.parseInt(args[i]);
            }
            setScreeningDateAndTime(nums[0], nums[1], nums[2], nums[3]);
        } catch (NumberFormatException ex) {
            logger.log(Level.INFO, "improper Screening Date values");
            setScreeningDateAndTime(Calendar.getInstance().getTime());
        }
    }

    public String getScreeningVenue() {
        return screeningVenue;
    }

    public void setScreeningVenue(String screeningVenue) {
        this.screeningVenue = screeningVenue;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVenueAddress1() {
        return venueAddress1;
    }

    public void setVenueAddress1(String venueAddress1) {
        this.venueAddress1 = venueAddress1;
    }

    public String getVenueAddress2() {
        return venueAddress2;
    }

    public void setVenueAddress2(String venueAddress2) {
        this.venueAddress2 = venueAddress2;
    }

    public String getVenuePhoneNumber() {
        return venuePhoneNumber;
    }

    public void setVenuePhoneNumber(String venuePhoneNumber) {
        this.venuePhoneNumber = venuePhoneNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int compareTo(FilmScreening that) {
        if (this == that) {
            return 0;
        }
        return this.screeningDateAndTime.compareTo(that.screeningDateAndTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilmScreening)) {
            return false;
        }
        FilmScreening that = (FilmScreening) o;
        if (this.key.getName().equals(that.key.getName()) &&
            this.screeningDateAndTime == that.screeningDateAndTime) {
                return true;
        } else {
            return false;
        }
    }


    public boolean deepEquals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FilmScreening other = (FilmScreening) obj;
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if ((this.directorFirst == null) ? (other.directorFirst != null) : !this.directorFirst.equals(other.directorFirst)) {
            return false;
        }
        if ((this.directorLast == null) ? (other.directorLast != null) : !this.directorLast.equals(other.directorLast)) {
            return false;
        }
        if ((this.synopsis == null) ? (other.synopsis != null) : !this.synopsis.equals(other.synopsis)) {
            return false;
        }
        if ((this.series == null) ? (other.series != null) : !this.series.equals(other.series)) {
            return false;
        }
        if (this.screeningDateAndTime != other.screeningDateAndTime && (this.screeningDateAndTime == null || !this.screeningDateAndTime.equals(other.screeningDateAndTime))) {
            return false;
        }
        if ((this.screeningVenue == null) ? (other.screeningVenue != null) : !this.screeningVenue.equals(other.screeningVenue)) {
            return false;
        }
        if ((this.venueAddress1 == null) ? (other.venueAddress1 != null) : !this.venueAddress1.equals(other.venueAddress1)) {
            return false;
        }
        if ((this.venueAddress2 == null) ? (other.venueAddress2 != null) : !this.venueAddress2.equals(other.venueAddress2)) {
            return false;
        }
        if ((this.venuePhoneNumber == null) ? (other.venuePhoneNumber != null) : !this.venuePhoneNumber.equals(other.venuePhoneNumber)) {
            return false;
        }
        if ((this.presenterFirst == null) ? (other.presenterFirst != null) : !this.presenterFirst.equals(other.presenterFirst)) {
            return false;
        }
        if ((this.presenterLast == null) ? (other.presenterLast != null) : !this.presenterLast.equals(other.presenterLast)) {
            return false;
        }
        return true;
    }



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 71 * hash + (this.screeningDateAndTime != null ? this.screeningDateAndTime.hashCode() : 0);
        return hash;
    }

}
