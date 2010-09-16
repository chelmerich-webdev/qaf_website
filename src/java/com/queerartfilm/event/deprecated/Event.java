package com.queerartfilm.event.deprecated;

import com.queerartfilm.film.Venue;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.queerartfilm.film.deprecated.Film;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.queerartfilm.series.deprecated.Series;
import com.queerartfilm.validation.IsDateP;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Id;

/**
 * This Java Bean models a Queer|Art|Film series event, that is, a film and screening
 * date, time, and location, as well as descriptive information. Annotated for
 * Objectify / GAE persistence.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Cached
public class Event implements Comparable<Event> {

    public static String ID_PATTERN = "MMMM yyyy";
    public static DateFormat ID_DATE_FORMATTER = new SimpleDateFormat(ID_PATTERN);
    public static String ID_STRING_FORMAT = "%s %s";
    private static final int SETTABLE_FIELD_COUNT = 11;
    
    @Id
    private long id;
    private Key<Film> filmKey;
    private Key<Series> parentKey;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String ampm;
    private String presenterFirst;
    private String presenterLast;
    private String synopsis;
    private Venue venue;
//    private boolean assigned;

    private static String[] initArgs() {
        String[] args = new String[SETTABLE_FIELD_COUNT];
        Date now = new Date();
        int idx = 0;
        args[idx++] = IsDateP.dfYear.format(now);
        args[idx++] = IsDateP.dfMonth.format(now);
        args[idx++] = "15"; //IsDateP.dfDay.format(now);
        args[idx++] = "8";
        args[idx++] = "00"; // IsDateP.dfMinute.format(now);
        args[idx++] = "PM";
        args[idx++] = "";   // Presenter First
        args[idx++] = "";   // Presenter Last
        args[idx++] = "";   // Synopsis
        args[idx++] = "IFC_CENTER";
        args[idx++] = "1"; // id

        if (idx != SETTABLE_FIELD_COUNT) {
            throw new AssertionError(Event.class.getSimpleName() + " improperly initialized!");
        }
        return args;
    }
//    Construtors 

    Event() {
        this(null, initArgs());
    }

    Event(Key<Film> filmKey) {
        this(filmKey, initArgs());
    }

    Event(Key<Film> filmKey, String... args) {
        int len = args.length;
        if (args.length != SETTABLE_FIELD_COUNT) {
            throw new IllegalArgumentException(String.format(
                    "Constructor must have key plus %d args, not %d", SETTABLE_FIELD_COUNT, len));
        }
        this.filmKey = filmKey;
        this.parentKey = null;
        int idx = 0;
        this.year = args[idx++];
        this.month = args[idx++];
        this.day = args[idx++];
        this.hour = args[idx++];
        this.minute = args[idx++];
        this.ampm = args[idx++];
        this.presenterFirst = args[idx++];
        this.presenterLast = args[idx++];
        this.synopsis = args[idx++];
        this.venue = Venue.valueOf(args[idx++]);
        this.id = Long.parseLong(args[idx]);

    }

//    Getters and Setters TODO
    public Key<Film> getFilmKey() {
        return filmKey;
    }

    public void setFilmKey(Key<Film> filmKey) {
        this.filmKey = filmKey;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Key<Series> getParentKey() {
        return parentKey;
    }

    public void setParentKey(Key<Series> parentKey) {
        this.parentKey = parentKey;
//        if (parentKey == null) {
//            assigned = false;
//        } else {
//            assigned = true;
//        }
    }

    public boolean isAssigned() {
        return parentKey != null;
    }

//    private void setAssigned(boolean assigned) {
//        this.assigned = assigned;
//    }

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

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
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

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        String monthAndYear = String.format(ID_STRING_FORMAT, month, year);
        sb.append(monthAndYear);
        sb.append(" at ");
        sb.append(venue.toString());
        return sb.toString();
    }
    
    public String toJSONString() throws JSONException {
//        String[] fields = {"hour", "minute", "ampm"};
//        System.out.println(Arrays.toString(fields));
        JSONObject jsonObj = new JSONObject(this);
        return jsonObj.toString(4);
    }

    /**
     * Default comparason is based on the notion of one event happening before
     * or after another and defers comparison long Id value.
     *
     * @param that the Event being compared.
     * @return the value of the comparison
     */
    @Override
    public int compareTo(Event that) {
        int result = 0;
        if (that == null) {
            result = 1;
        } else if (this.equals(that)) {
            result = 0;
        } else if (this.id > that.id) {
            result = 1;
        } else {
            result = -1;
        }
        return result;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
}
