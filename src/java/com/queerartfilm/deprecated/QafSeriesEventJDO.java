package com.queerartfilm.deprecated;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import java.util.Date;
import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * This Java Bean models a Queer|Art|Film series event, that is, a film and screening
 * date, time, and location, as well as descriptive information.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Deprecated
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class QafSeriesEventJDO implements Comparable<QafSeriesEventJDO>{

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    Key key;
    @Persistent
    private Film film;
    @Persistent
    private Screening screening;
    @Persistent
    private String series;
    @Persistent
    private String presenterFirst;
    @Persistent
    private String presenterLast;
    @Persistent
    private Text synopsis;
//    @Persistent
//    private List<ExternalResource> resources;


    public QafSeriesEventJDO(Film film, Screening screening) {
        this.film = film;
        this.screening = screening;
    }

    public QafSeriesEventJDO() {
        this(new Film(), new Screening());
    }
    //    Getters and Setters

    public void setFilm(Film film) {
        this.film = film;
    }

    public Film getFilm() {
        return film;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Screening getScreening() {
        return screening;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
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

//    public List<ExternalResource> getResources() {
//        return resources;
//    }
//
//    public void setResources(List<ExternalResource> resources) {
//        this.resources = resources;
//    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Text getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(Text synopsis) {
        this.synopsis = synopsis;
    }


    /**
     * Default comparason is based on the notion of one event happening before
     * or after another and defers comparison to the screening date and time.
     */
    @Override
    public int compareTo(QafSeriesEventJDO that) {
        return this.screening.compareTo(that.screening);
    }

    /**
     * Overrides <code>Object#equals</code> and defines equality of two
     * <code>QafSeriesEvents</code>s on the <code>film</code> and 
     * <code>screening</code> values only.
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
        final QafSeriesEventJDO other = (QafSeriesEventJDO) obj;
        if (this.film != other.film && (this.film == null || !this.film.equals(other.film))) {
            return false;
        }
        if (this.screening != other.screening && (this.screening == null || !this.screening.equals(other.screening))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.film != null ? this.film.hashCode() : 0);
        hash = 89 * hash + (this.screening != null ? this.screening.hashCode() : 0);
        return hash;
    }

    /**
     * Java Bean that models a QAF series film selection
     *
     * @author Curt Helmerich
     * @author ch67dev@gmail.com
     */
    @PersistenceCapable
    @EmbeddedOnly
    public static class Film {

        @Persistent
        private String title; //*
        @Persistent
        private int year; //*
        @Persistent
        private String directorFirst;
        @Persistent
        private String directorLast; //*
        @Persistent
        private String mpaaRating;
        @Persistent
        private int minutes;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public String getMpaaRating() {
            return mpaaRating;
        }

        public void setMpaaRating(String mpaaRating) {
            this.mpaaRating = mpaaRating;
        }

        /**
         * Overrides <code>Object#equals</code> and defines equality of two
         * <code>Film</code>s on <code>title</code>, <code>year</code>,
         * and <code>directorLast</code> values only.
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
            final Film other = (Film) obj;
            if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
                return false;
            }
            if (this.year != other.year) {
                return false;
            }
            if ((this.directorLast == null) ? (other.directorLast != null) : !this.directorLast.equals(other.directorLast)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + (this.title != null ? this.title.hashCode() : 0);
            hash = 29 * hash + this.year;
            hash = 29 * hash + (this.directorLast != null ? this.directorLast.hashCode() : 0);
            return hash;
        }
    }

    /**
     * Java Bean that models a QAF series screening date, time, and venue.
     *
     * @author Curt Helmerich
     * @author ch67dev@gmail.com
     */
    @PersistenceCapable
    @EmbeddedOnly
    public static class Screening implements Comparable<Screening> {

        @Persistent
        private Date showDateAndTime;
        @Persistent
        private String venue;

        /**
         * Returns a defensive copy of the <code>Date</code> value.
         * @return a defensive copy of the <code>Date</code> value.
         */
        public Date getShowDateAndTime() {
            return new Date(showDateAndTime.getTime());
        }

        public void setShowDateAndTime(Date showDateAndTime) {
            this.showDateAndTime = showDateAndTime;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

        public int compareTo(Screening that) {
            return this.showDateAndTime.compareTo(that.showDateAndTime);
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
            if (this.showDateAndTime != other.showDateAndTime && (this.showDateAndTime == null || !this.showDateAndTime.equals(other.showDateAndTime))) {
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
            hash = 29 * hash + (this.showDateAndTime != null ? this.showDateAndTime.hashCode() : 0);
            hash = 29 * hash + (this.venue != null ? this.venue.hashCode() : 0);
            return hash;
        }
    }
}
