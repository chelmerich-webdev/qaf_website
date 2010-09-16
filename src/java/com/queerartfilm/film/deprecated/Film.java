package com.queerartfilm.film.deprecated;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.queerartfilm.event.deprecated.Event;
import com.queerartfilm.film.Rating;
import com.queerartfilm.model.Utils;
import javax.persistence.Id;

/**
 * Java Bean that models a QAF series film selection and annotated for
 * Objectify / GAE persistence.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Cached
public class Film {
    public static final Film NULL = new Film("null-film", 1970, "", "", Rating.NR, 0);
    @Id
    private String id;
    private String title;
    private Integer year;
    private String directorLast;
//    @Unindexed
    private Key<Event> parentKey;
//    @Unindexed
    private String directorFirst;
//    @Unindexed
    private Rating mpaaRating;
//    @Unindexed
    private Integer length;
    private boolean assigned;

    Film(String title, Integer year, String directorLast) {
        this();
        this.setTitle(title);
        this.setYear(year);
        this.setDirectorLast(directorLast);
    }

    public Film(String title, Integer year, String directorLast,
            String directorFirst, Rating mpaaRating, Integer length) {
        this.setTitle(title);
        this.setYear(year);
        this.setDirectorLast(directorLast);
        this.setDirectorFirst(directorFirst);
        this.setMpaaRating(mpaaRating);
        this.setLength(length);
        this.setId(this.getTitle());
        this.setAssigned(false);
    }

    public Film() {
        this("", new Integer(0), "", "", Rating.NR, new Integer(0));
    }

    public void setId(String title) {
        this.id = Utils.createID(title);
    }

    public String getId() {
        return id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer lengthInMinutes) {
        this.length = lengthInMinutes;
    }

    public Rating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(Rating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Key<Event> getParentKey() {
        return parentKey;
    }

    public void setParentKey(Key<Event> parentKey) {
        this.parentKey = parentKey;
//        if (parentKey == null) {
//            assigned = false;
//        } else {
//            assigned = true;
//        }
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
    public boolean isAssigned() {
        return parentKey != null;
    }
    /**
     * Overrides <code>Object#equals</code> and defines equality of two
     * <code>Film</code>s on <code>id</code>, <code>year</code>,
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
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if (this.year != other.year && (this.year == null || !this.year.equals(other.year))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 79 * hash + this.year;
        return hash;
    }

    @Override
    public String toString() {
        return title;
    }

    public String toJSONString() throws JSONException {
        JSONObject jsonObj = new JSONObject(this);
        return jsonObj.toString(4);
    }
}
