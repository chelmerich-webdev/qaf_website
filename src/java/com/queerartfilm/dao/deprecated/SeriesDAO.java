package com.queerartfilm.dao.deprecated;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.queerartfilm.dao.AbstractOfyDAO;
import com.queerartfilm.event.deprecated.Event;
import com.queerartfilm.series.deprecated.Series;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class SeriesDAO extends AbstractOfyDAO<Series> {

    private static final Logger logger = Logger.getLogger(SeriesDAO.class.getName());
    private static final SeriesDAO DAO = new SeriesDAO();

    /**
     * Custom EL Function, spec'ed in the qaf.tld
     *
     * @param id
     * @return the Film that matches this id
     */
    public static Series getSeriesByKey(Key<Series> key) {
        return DAO.find(key);
    }

    static {
        ObjectifyService.register(Series.class);
    }

    public SeriesDAO() {
        super(Series.class, "title");
    }

    public Series find(String idAsString) {
        long id = 0L;
        try {
            id = Long.parseLong(idAsString);
        } catch (NumberFormatException ex) {
            logger.warning("Could not parse Event id=" + idAsString);
            return null;
        }
        return this.find(id);
    }

    public Series find(long id) {
        return ofy().find(clazz, id);
    }

    @Override
    public Key<Series> save(Series series) {
        // if not new, that is, doesn't have a null ID as it is set by Obejectify
        if (series.getId() != null) {

            // get previous value if any
            Series prev = this.find(series.getId());
            if (prev != null) { // existing series
                for (Key<Event> key : prev.getEvents()) {
                    Event event = ofy().find(key);

                    if (event != null) {
                        event.setParentKey(null);
                        ofy().put(event);
                    }
                }
            }
        }
        Key<Series> result = ofy().put(series);
        for (Key<Event> key : series.getEvents()) {
            Event event = ofy().find(key);
            if (event != null) {
                event.setParentKey(result);
                ofy().put(event);
            }
        }
        return result;

    }

    @Override
    public void delete(Series series) {

        Map<Key<Event>, Event> events = ofy().get(series.getEvents());
        if (!events.isEmpty()) {
            for (Event event : events.values()) {
                event.setParentKey(null);
                ofy().put(event);
            }
        }
        
        ofy().delete(series);
    }
}
