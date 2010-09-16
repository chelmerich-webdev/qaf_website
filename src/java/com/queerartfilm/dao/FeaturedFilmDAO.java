/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.dao;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;
import com.queerartfilm.film.FeaturedFilm;
import com.queerartfilm.series.QAFSeries;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class FeaturedFilmDAO extends DAOBase {

    public static final Comparator<Key<FeaturedFilm>> KEY_SORTER = new KeySorter();
    private static final Class<FeaturedFilm> clazz = FeaturedFilm.class;
    private static final Logger logger = Logger.getLogger(FeaturedFilmDAO.class.getName());
    private static final FeaturedFilmDAO dao = new FeaturedFilmDAO();

    static {
        ObjectifyService.register(clazz);
    }

    /** Custom EL Function, spec'ed in the qaf.tld
     *
     * @param id
     * @return the Film that matches this id
     */
    public static FeaturedFilm getFeaturedFilmByKey(Key<FeaturedFilm> key) {
        if (key == null) {
            return null;
        }
        return dao.find(key.getId());
    }

    public static List<Key<FeaturedFilm>> getAssignable(long seriesId) {
        Objectify ofy = ObjectifyService.begin();
        List<Key<FeaturedFilm>> filmList =
                Lists.newArrayList(ofy.query(clazz).filter("assigned", Boolean.FALSE).listKeys());

        if (seriesId <= 0) {
            return filmList;
        }
        Key<QAFSeries> seriesKey = new Key<QAFSeries>(QAFSeries.class, seriesId);
        QAFSeries series = ofy.find(seriesKey);
        if (series != null) {
            for (Key<FeaturedFilm> key : series.getFilmKeys()) {
                filmList.add(key);
            }
        }

        // TODO: Sort films
        return filmList;

    }

    /**
     * Store entity in the datastore. Will overwrite an entity with same key.
     * @param film FeaturedFilm entity to be saved to the datastore
     * @return the Key for this entity
     */
    public Key<FeaturedFilm> save(FeaturedFilm film) {

        return ofy().put(film);
    }
    // read (get)

    public FeaturedFilm get(long id) {
        FeaturedFilm result = null;
        try {
            result = ofy().get(clazz, id);
        } catch (NotFoundException ex) {
            logger.warning("FeaturedFilm not found for id = " + ex.getKey().getId());
        }
        return result;
    }

    public List<FeaturedFilm> getAllItems() {
        List<FeaturedFilm> result = ofy().query(clazz).list();
        Collections.sort(result);
        Collections.reverse(result);
        return result;
    }

    public List<FeaturedFilm> getAllAssigned() {
        List<FeaturedFilm> result = queryOn("assigned", true);
        Collections.sort(result);
        Collections.reverse(result);
        return result;
    }

    public List<FeaturedFilm> getAllUnassigned() {
        List<FeaturedFilm> result = queryOn("assigned", false);
        Collections.sort(result);
        Collections.reverse(result);
        return result;
    }

    private List<FeaturedFilm> queryOn(String property, Object value) {
        return query().filter(property, value).list();
    }
    public List<Key<FeaturedFilm>> getAllItemKeys() {
        return ofy().query(clazz).listKeys();
    }

    public Query<FeaturedFilm> query() {
        return ofy().query(clazz);
    }

    public FeaturedFilm find(long id) {
        return ofy().find(clazz, id);
    }
    // getOrCreate (returns a new thing if not found in datastore vs. null or exception)

    // update
    public Key<FeaturedFilm> update(FeaturedFilm updatedState) {
        boolean isNull = updatedState == null;
        if (isNull) {
            throw new NullPointerException("Attempt to update a null QAFSeries!");
        }

        boolean isNew = updatedState.getId() == null
                || ofy().find(clazz, updatedState.getId()) == null;

        if (isNew) { // then it is a new entity
            Key<FeaturedFilm> newKey = save(updatedState);
            updateParent(updatedState, true);
            return newKey;
        }
        // otherwise entity has an id and is in the datastore
        FeaturedFilm storedState = get(updatedState.getId());
        isNull = storedState == null;
        if (isNull) {
            throw new NullPointerException("Attempt to update a null QAFSeries!");
        }
        boolean storedAssigned = (boolean) storedState.isAssigned();
        boolean updatedAssigned = (boolean) updatedState.isAssigned();

        boolean bothNull = !storedAssigned && ! updatedAssigned;
        boolean bothAssigned = storedAssigned &&  updatedAssigned;
        boolean bothSameParent = bothNull
                || (bothAssigned && storedState.getSeriesKey().equals(updatedState.getSeriesKey()));

        if (!bothSameParent) {
            // let the updateParent method handle any null values.

            updateParent(storedState, false);
            updateParent(updatedState, true);
        }

        return save(updatedState);
    }

    /**
     * Deletes film from datastore, also updating inclusion in any series.
     *
     * @param film FeaturedFilm to be deleted. 
     */
    public void delete(FeaturedFilm film) {
        updateParent(film, false);
        ofy().delete(film);
    }

    private void updateParent(FeaturedFilm feature, boolean related) {
        if (feature.getSeriesKey() == null) {
            return;
        }
        QAFSeries series = ofy().get(feature.getSeriesKey());
        Key<FeaturedFilm> key = new Key<FeaturedFilm>(FeaturedFilm.class, feature.getId());

        if (related) {
            series.addFilmKey(key);
        } else if (!related) {
            series.removeFilmKey(key);
        }
        ofy().put(series);

    }

    private static class KeySorter implements Comparator<Key<FeaturedFilm>> {

        @Override
        public int compare(Key<FeaturedFilm> thisKey, Key<FeaturedFilm> thatKey) {
            FeaturedFilmDAO dao = new FeaturedFilmDAO();
            FeaturedFilm thisFilm = dao.find(thisKey.getId());
            FeaturedFilm thatFilm = dao.find(thatKey.getId());

            if (thisFilm == null && thatFilm == null) {
                return 0;
            } else if (thisFilm == null) {
                return -1;
            } else if (thatFilm == null) {
                return 1;
            }

            return thisFilm.compareTo(thatFilm);
        }
    }
}
