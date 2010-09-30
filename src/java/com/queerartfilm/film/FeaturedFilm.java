package com.queerartfilm.film;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import com.queerartfilm.model.Utils;
import com.queerartfilm.series.QAFSeries;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.PrePersist;

/**
 * Java Bean transfer object for a featured film.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Cached
@Unindexed
public class FeaturedFilm implements Serializable, Comparable<FeaturedFilm> {

    private static final Logger logger = Logger.getLogger(FeaturedFilm.class.getName());
    @Id
    private Long id;
    @Indexed
    private String urlKey;
    private Key<QAFSeries> seriesKey;
    @Embedded
    private Person presenter;
    private String synopsis;
    private String title;
    @Embedded
    private Person director;
    private Integer releaseYear;
    private Integer length;
    private Rating rating;
    @Embedded
    Screening screening;
    @Indexed
    boolean assigned;
    @Embedded
    List<Link> links = new ArrayList<Link>();

    public FeaturedFilm() {
        this("", new Screening(), new Person(), new Person());
    }

    public FeaturedFilm(String title, Screening screening, Person director, Person presenter) {
        this.title = title;
        this.screening = screening;
        this.director = director;
        this.presenter = presenter;
//        this.assigned = true;
    }

    // Use @PrePersist to set derived property values before persisting

    @PrePersist
    void updateComputedProperties() {
        setUrlKey();
        setAssigned();
        getScreening().setPast();
        getScreening().setOnSale();
    }

    public List<Link> getLinks() {
        return new ArrayList<Link>(links);
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public boolean addLink(Link link)  {
        return links.add(link);
    }

    public boolean removeLink(Link link) {
        return links.remove(link);
    }

    public void clearLinks() {
        links.clear();
    }
    
    public Person getDirector() {
        return director;
    }

    public String getDirectorFirst() {
        return director.getFirst();
    }

    public void setDirectorFirst(String directorFirst) {
        this.director.setFirst(directorFirst);
    }

    public String getDirectorLast() {
        return director.getLast();
    }

    public void setDirectorLast(String directorLast) {
        this.director.setLast(directorLast);
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Person getPresenter() {
        return presenter;
    }

    public String getPresenterFirst() {
        return presenter.getFirst();
    }

    public void setPresenterFirst(String presenterFirst) {
        this.presenter.setFirst(presenterFirst);
    }

    public String getPresenterLast() {
        return presenter.getLast();
    }

    public void setPresenterLast(String presenterLast) {
        this.presenter.setLast(presenterLast);
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Screening getScreening() {
        return screening;
    }

    public Key<QAFSeries> getSeriesKey() {
        return seriesKey;
    }

    public void setSeriesKey(Key<QAFSeries> seriesKey) {
        this.seriesKey = seriesKey;
    }

    public boolean isAssigned() {
        return assigned;
    }

    /**
     * This setter sets a derived value so takes no parameters.
     */
    private void setAssigned() {
        this.assigned = seriesKey != null;
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

    public String getUrlKey() {
        return urlKey;
    }

    /**
     * This setter sets a derived value so takes no parameters.
     */
    private void setUrlKey() {
        this.urlKey = Utils.createId(this.title);
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return String.format("%s presents %s", presenter, title);
    }

    @Override
    public int compareTo(FeaturedFilm that) {
        return this.screening.compareTo(that.screening);
    }
}
