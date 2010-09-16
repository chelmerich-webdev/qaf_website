package com.queerartfilm.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;
import com.queerartfilm.film.FeaturedFilm;
import com.queerartfilm.series.QAFSeries;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class QAFSeriesDAO extends DAOBase implements DAO<QAFSeries> {

    private static final Class<QAFSeries> clazz = QAFSeries.class;
    private static final Logger logger = Logger.getLogger(QAFSeriesDAO.class.getName());
    private static final QAFSeriesDAO dao = new QAFSeriesDAO();
    static {
        ObjectifyService.register(clazz);
    }

        /**
     * Custom EL Function, spec'ed in the qaf.tld
     *
     * @param id
     * @return the Film that matches this id
     */
    public static QAFSeries getQAFSeriesByKey(Key<QAFSeries> key) {
        if (key == null) {
            return null;
        }
        return dao.find(key.getId());
    }


    /**
     * Store entity in the datastore. Will overwrite an entity with same key.
     * @param film FeaturedFilm entity to be saved to the datastore
     * @return the Key for this entity
     */
    public Key<QAFSeries> save(QAFSeries series) {

        return ofy().put(series);
    }

    public QAFSeries get(long id) {
        QAFSeries result = null;
        try {
            result = ofy().get(clazz, id);
        } catch (NotFoundException ex) {
            logger.warning("FeaturedFilm not found for id = " + ex.getKey().getId());
        }
        return result;
    }


    /**
     * Get a descending list of all <code>QAFSeries</code> entities.
     * 
     * @return a descending list of all <code>QAFSeries</code> entities.
     */
    public List<QAFSeries> getAllItems() {
        List<QAFSeries> result = ofy().query(clazz).list();
        Collections.sort(result);
        Collections.reverse(result);
        return result;
    }

    public List<Key<QAFSeries>> getAllItemKeys() {
        return ofy().query(clazz).listKeys();
    }
 
    public QAFSeries find(long id) {
        return ofy().find(clazz, id);
    }

    public void delete(QAFSeries series) {
        orphan(series.getFilmKeys());
        ofy().delete(series);
    }

    public Key<QAFSeries> update(QAFSeries updatedEntity) {
        boolean isNull = updatedEntity == null;
        if (isNull) {
            throw new NullPointerException("Attempt to update a null QAFSeries!");
        }
        
        //  test if new, either by no id or if id, not found
        boolean isNew = updatedEntity.getId() == null ||
                        ofy().find(clazz, updatedEntity.getId()) == null;

        if (isNew) {
            // save first to set id, then update children
            Key<QAFSeries> newKey = save(updatedEntity);
            adopt(updatedEntity.getFilmKeys(), newKey);
            return newKey;
        }

        //  this entity exists in datastore so get that stored value
        QAFSeries storedEntity = this.get(updatedEntity.getId());
        boolean hasChildren =
                !storedEntity.getFilmKeys().isEmpty() ||
                !updatedEntity.getFilmKeys().isEmpty();

        if (hasChildren) {
            updateChildren(storedEntity, updatedEntity);
        }
        return save(updatedEntity);

    }

    private void updateChildren(QAFSeries oldState, QAFSeries newState) {
        Set<Key<FeaturedFilm>> bothStates = oldState.getFilmKeys();
        bothStates.retainAll(newState.getFilmKeys());

        // get all previous keys not in the intersection, which are now orphaned
        Set<Key<FeaturedFilm>> orphanedFilms = new HashSet<Key<FeaturedFilm>>();
        for (Key<FeaturedFilm> filmKey : oldState.getFilmKeys()) {
            if (bothStates.contains(filmKey)) {   // in both, so skip
                continue;
            } else {                                // in prev, but not in next
                orphanedFilms.add(filmKey);
            }
        }
        orphan(orphanedFilms);

        // get all new keys not in the intersection, which are now adopted
        Set<Key<FeaturedFilm>> adoptedFilms = new HashSet<Key<FeaturedFilm>>();
        for (Key<FeaturedFilm> filmKey : newState.getFilmKeys()) {
            if (bothStates.contains(filmKey)) {   // in both, so skip
                continue;
            } else {
                adoptedFilms.add(filmKey);          // in next, but not in prev
            }
        }

        // set new parent and save
        Key<QAFSeries> seriesKey = new Key<QAFSeries>(clazz, newState.getId());
        adopt(adoptedFilms, seriesKey);
    }

    private void adopt(Collection<Key<FeaturedFilm>> filmKeys, Key<QAFSeries> seriesKey) {
        Map<Key<FeaturedFilm>, FeaturedFilm> map = ofy().get(filmKeys);
        for (FeaturedFilm ff : map.values()) {
            ff.setSeriesKey(seriesKey);
            ofy().put(ff);
        }
    }

    private void orphan(Collection<Key<FeaturedFilm>> filmKeys) {
        Map<Key<FeaturedFilm>, FeaturedFilm> map = ofy().get(filmKeys);
        for (FeaturedFilm ff : map.values()) {
            ff.setSeriesKey(null);
            ofy().put(ff);
        }
    }

}
