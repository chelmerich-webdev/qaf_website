  package com.queerartfilm.series;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.queerartfilm.dao.FeaturedFilmDAO;
import com.queerartfilm.film.FeaturedFilm;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.Id;

/**
 * Java Bean transfer object representing a Queer|Art|Film seasonal series and
 * the corresponding featured films. 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Cached
public class QAFSeries implements Serializable, Comparable<QAFSeries> {

    @Id
    private Long id;
    private String title;
    private SortedSet<Key<FeaturedFilm>> filmKeys;

    public QAFSeries() {
        filmKeys = new TreeSet<Key<FeaturedFilm>>(FeaturedFilmDAO.KEY_SORTER);
    }

    public SortedSet<Key<FeaturedFilm>> getFilmKeys() {
        SortedSet<Key<FeaturedFilm>> result =
                new TreeSet<Key<FeaturedFilm>>(FeaturedFilmDAO.KEY_SORTER);
        return new TreeSet<Key<FeaturedFilm>>(filmKeys);
    }

    public List<Key<FeaturedFilm>> getFilmKeysAsListAsc() {
        List<Key<FeaturedFilm>> result = new ArrayList<Key<FeaturedFilm>>(filmKeys);
        return result;
    }

    public List<Key<FeaturedFilm>> getFilmKeysAsListDesc() {
        List<Key<FeaturedFilm>> result = getFilmKeysAsListAsc();
        Collections.reverse(result);
        return result;
    }

    public void setFilmKeys(SortedSet<Key<FeaturedFilm>> filmKeys) {
        this.filmKeys = filmKeys;
    }

    public void clearFilmKeys() {
        this.filmKeys = new TreeSet<Key<FeaturedFilm>>(FeaturedFilmDAO.KEY_SORTER);
    }

    public boolean addFilmKey(Key<FeaturedFilm> key) {
        return this.filmKeys.add(key);
    }

    public boolean removeFilmKey(Key<FeaturedFilm> key) {
        return this.filmKeys.remove(key);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
    
    public int compareTo(QAFSeries that) {
        if (this == that) {
            return 0;
        }
        if (this.getFilmKeys().isEmpty() && that.getFilmKeys().isEmpty()) {
            return 0;
        } else if (this.getFilmKeys().isEmpty()) {
            return -1;
        } else if (that.getFilmKeys().isEmpty()) {
            return 1;
        }

        // if both series have a not empty film list, then compare last one in each
        return this.filmKeys.last().compareTo(that.filmKeys.last());


    }
}
