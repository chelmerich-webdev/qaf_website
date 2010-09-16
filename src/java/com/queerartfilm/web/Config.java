package com.queerartfilm.web;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.queerartfilm.film.FeaturedFilm;
import com.queerartfilm.series.QAFSeries;
import javax.persistence.Id;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Cached
public class Config {

    public static final String ID = "*";
    @Id
    String id;
    private Key<QAFSeries> qafSeriesKey;
//    private Key<Series> series;
//    private Key<Event> event;
    private Key<FeaturedFilm> featuredFilmKey;
    private String year;
    private String facebookUrl;
    private String subscribeEmailAddress;
    private String subscribeEmailSubject;
    private String subscribeEmailBody;

    Config() {
        this.id = ID;
    }

    public Key<FeaturedFilm> getFeaturedFilmKey() {
        return featuredFilmKey;
    }

    public void setFeaturedFilmKey(Key<FeaturedFilm> featuredFilmKey) {
        this.featuredFilmKey = featuredFilmKey;
    }

    public Key<QAFSeries> getQafSeriesKey() {
        return qafSeriesKey;
    }

    public void setQafSeriesKey(Key<QAFSeries> qafSeriesKey) {
        this.qafSeriesKey = qafSeriesKey;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getSubscribeEmailAddress() {
        return subscribeEmailAddress;
    }

    public void setSubscribeEmailAddress(String subscribeEmailAddress) {
        this.subscribeEmailAddress = subscribeEmailAddress;
    }

    public String getSubscribeEmailBody() {
        return subscribeEmailBody;
    }

    public void setSubscribeEmailBody(String subscribeEmailBody) {
        this.subscribeEmailBody = subscribeEmailBody;
    }

    public String getSubscribeEmailSubject() {
        return subscribeEmailSubject;
    }

    public void setSubscribeEmailSubject(String subscribeEmailSubject) {
        this.subscribeEmailSubject = subscribeEmailSubject;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format(
                "[QafSeriesId=%s, featureId=%s]", qafSeriesKey, featuredFilmKey);
    }
}
