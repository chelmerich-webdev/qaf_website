package com.queerartfilm.film;

import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.appengine.repackaged.org.json.JSONString;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 *  Embedded Java Bean transfer object for a QAF screening date/time and location.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class Screening implements Serializable, Comparable<Screening>, JSONString {

    private static Logger logger = Logger.getLogger(Screening.class.getName());
    // default format = date LONG time SHORT = MMMM d, yyyy h:mm a
    public static String defaultStringFormat = ("%s %s, %s %s:%s %s");
    public static DateFormat df = DateFormat.getDateTimeInstance(
            DateFormat.LONG, DateFormat.SHORT);//, new Locale("en", "US"));
    // Convenience formatters that correspond to default format
    public static DateFormat dfYear = new SimpleDateFormat("yyyy");
    public static DateFormat dfMonth = new SimpleDateFormat("MMMM");
    public static DateFormat dfDay = new SimpleDateFormat("d"); // no zero-padding
    public static DateFormat dfHour = new SimpleDateFormat("h"); // no zero-padding
    public static DateFormat dfMinute = new SimpleDateFormat("mm");
    public static DateFormat dfAmpm = new SimpleDateFormat("a");

    // EL function declarations
    public static String getDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return df.format(date);
    }

    public static String getDate(Date date) {
        if (date == null) {
            return "";
        }
        return DateFormat.getDateInstance(DateFormat.LONG).format(date);
    }

    public static String getTime(Date date) {
        if (date == null) {
            return "";
        }
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
    }

    public static String getYear(Date date) {
        if (date == null) {
            return "";
        }
        return dfYear.format(date);
    }

    public static String getMonth(Date date) {
        if (date == null) {
            return "";
        }
        return dfMonth.format(date);
    }

    public static String getDay(Date date) {
        if (date == null) {
            return "";
        }
        return dfDay.format(date);
    }

    public static String getHour(Date date) {
        if (date == null) {
            return "";
        }
        return dfHour.format(date);
    }

    public static String getMinute(Date date) {
        if (date == null) {
            return "";
        }
        return dfMinute.format(date);
    }

    public static String getAmpm(Date date) {
        if (date == null) {
            return "";
        }
        return dfAmpm.format(date);
    }
    private Date date;
    private Venue venue;
    private String purchaseUrl;
    private boolean onSale;
    private boolean past;
    private String secondTime;

    public Screening() {
        this.date = new Date();
        this.venue = Venue.values()[0];
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        this.purchaseUrl = purchaseUrl;
    }

    public String getSecondTime() {
        return secondTime;
    }

    public void setSecondTime(String secondTime) {
        this.secondTime = secondTime;
    }

    /**
     * If onSale is true, check if purchaseable and not yet past via setter.
     * Once false, always false.
     * @return <code>true</code> if on sale, <code>false</code> otherwise.
     */
    public boolean isOnSale() {
        if (onSale) {
            this.setOnSale();
        }
        return onSale;
    }

    /**
     * This package-private setter sets a derived value so takes no parameters.
     */
    void setOnSale() {
        boolean noScreeningDate = this.date == null;
        boolean noPurchaseUrl = this.purchaseUrl == null || "".equals(this.purchaseUrl);

        if (noScreeningDate || noPurchaseUrl || isPast()) {
            this.onSale = false;
        } else {
            this.onSale = true;
        }
    }

    /**
     * If past is false, check if against current date. Once false, always false.
     * @return <code>true</code> if past, <code>false</code> otherwise.
     */
    public boolean isPast() {
        if (!past) {
            this.setPast();
        }
        return past;
    }

    void setPast() {
        if (date == null) {
            past = false;
            return;
        }

        Date today = getDateOnly(new Date());
        Date showdate = getDateOnly(this.date);
        this.past = today.after(showdate);
    }

    private Date getDateOnly(Date date) {
        // strip out time values
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(date);
        c.clear(Calendar.HOUR);
        c.clear(Calendar.HOUR_OF_DAY);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        c.clear(Calendar.AM_PM);
        return c.getTime();
    }

    @Override
    public int compareTo(Screening that) {
        return this.date.compareTo(that.date);

    }

    @Override
    public String toString() {
        return String.format("%s at %s", df.format(this.date), this.venue);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Screening other = (Screening) obj;
        if (this.date != other.date && (this.date == null || !this.date.equals(other.date))) {
            return false;
        }
        if (this.venue != other.venue && (this.venue == null || !this.venue.equals(other.venue))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.date != null ? this.date.hashCode() : 0);
        hash = 97 * hash + (this.venue != null ? this.venue.hashCode() : 0);
        return hash;
    }

    @Override
    public String toJSONString() {
        String result = null;
        JSONObject json = new JSONObject();
        try {
            json.put("date", this.getDate().getTime());
            json.put("venue", this.getVenue());
            json.put("purchaseUrl", this.getPurchaseUrl());
            json.put("onSale", this.isOnSale());
            


            result = json.toString(2);
        } catch (Exception e) {
        }
        return result;
    }

    public static void main(String[] args) {
        Screening s = new Screening();
        System.out.println(s.toJSONString());
    }
}
