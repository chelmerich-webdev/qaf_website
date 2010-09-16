package com.queerartfilm.dao.deprecated;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.queerartfilm.dao.AbstractOfyDAO;
import com.queerartfilm.event.deprecated.Event;
import com.queerartfilm.film.deprecated.Film;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Objectify / GAE DAO for <code>EventDAO</code> entities
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class EventDAO extends AbstractOfyDAO<Event> {

    private static final String SORT_ORDER = "-id";
    private static final Logger logger = Logger.getLogger(EventDAO.class.getName());
//    private static final FilmDAO filmDAO = new FilmDAO();
    private static final EventDAO eventDAO = new EventDAO();

    /**
     * Custom EL Function, spec'ed in the qaf.tld
     *
     * @param id
     * @return the Film that matches this id
     */
    public static Event getEventByKey(Key<Event> key) {
        return eventDAO.find(key);
    }

    /**
     * Custom EL Function, spec'ed in the qaf.tld
     *
     * @param events "MMMM yyyy" strings
     * @return the Events that match these events
     */
    public static Map<Long, Event> getSeries(String... events) {
        int len = events.length;
        Long[] ids = new Long[len];
        for (int j = 0; j < len; j++) {
            try {
                long id = Event.ID_DATE_FORMATTER.parse(events[j]).getTime();
                ids[j] = Long.valueOf(id);
            } catch (ParseException ex) {
                logger.warning("Bad Date String: " + ex.getMessage());
            }
        }

        Map<Long, Event> result = eventDAO.ofy().get(eventDAO.clazz, ids);
        return result;
    }

    static {
        ObjectifyService.register(Event.class);
    }

    public EventDAO() {
        super(Event.class, SORT_ORDER);
    }

    /**
     * ID is set based only on Month and Year values because there can only
     * be one event per month.
     * 
     * @param event
     * @return Event Key
     */
    @Override
    public Key<Event> save(Event event) {
        // get any previous an update
        Event prev = this.find(event.getId());
        if (prev != null) { // do an update
            Film prevFilm = ofy().find(prev.getFilmKey());
            if (prevFilm != null) {
                prevFilm.setParentKey(null);
                ofy().put(prevFilm);
            }
        }
        // confirm keys are valid
        Film film = ofy().find(event.getFilmKey());
        if (film != null) {
            String dateString = String.format(
                    Event.ID_STRING_FORMAT, event.getMonth(), event.getYear());
            long id = -1L;
            try {
                id = Event.ID_DATE_FORMATTER.parse(dateString).getTime();
            } catch (ParseException ex) {
                logger.warning(ex.getMessage());
            }
            event.setId(id);
            Key<Event> key = super.save(event);

            // update field on film so it knows it is assigned to an event
            film.setParentKey(key);
            ofy().put(film);

            return key;
        } else {
            return null;
        }
    }

    @Override
    public void delete(Event event) {

        Film film = ofy().find(event.getFilmKey());
        if (film != null) {
            ofy().delete(event);
            film.setParentKey(null);
            ofy().put(film);
        }

    }

    public Event find(long id) {
        return ofy().find(clazz, id);
    }

    public Event find(String idAsString) {
        long id = 0L;
        try {
            id = Long.parseLong(idAsString);
        } catch (NumberFormatException ex) {
            logger.warning("Could not parse Event id=" + idAsString);
            return null;
        }
        return this.find(id);
    }
}
