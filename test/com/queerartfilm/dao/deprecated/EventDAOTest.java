package com.queerartfilm.dao.deprecated;

import com.queerartfilm.dao.deprecated.FilmDAO;
import com.queerartfilm.dao.deprecated.EventDAO;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.queerartfilm.film.deprecated.Film;
import com.queerartfilm.film.deprecated.FilmFactory;
import com.queerartfilm.event.deprecated.Event;
import com.queerartfilm.event.deprecated.EventFactory;
import java.text.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ch67dotnet
 */
public class EventDAOTest {

    EventDAO eventDAO;
    Event event;
    Key<Event> eventKey;
    FilmDAO filmDAO;
    Film film;
    Key<Film> filmKey;

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig(),
            new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() throws ParseException {
        helper.setUp();
        eventDAO = new EventDAO();
        filmDAO = new FilmDAO();
        
        film = FilmFactory.get().newFilm("Star Wars", "1977", "Lucas");
        filmKey = filmDAO.save(film);

        event = EventFactory.get().newEvent(filmKey);
        eventKey = eventDAO.save(event);
    }

    @After
    public void tearDown() {
        helper.tearDown();
        eventDAO = null;
        filmDAO = null;
        film = null;
        event = null;
        eventKey = null;

    }

//    @Test
//    public void testMemcache() {
//        MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
//        ArrayList<com.google.appengine.api.datastore.Key> keyList =
//                new ArrayList<com.google.appengine.api.datastore.Key>();
//        keyList.add(filmDAO.fact().getRawKey(filmKey));
//        System.out.println("Get All: " + ms.getAll(keyList));
//        System.out.println("Memcache size: " + ms.getStatistics().getItemCount());
//        assertNull(ms.get(filmDAO.fact().getRawKey(filmKey)));
//        assertTrue(ms.contains(filmDAO.fact().getRawKey(filmKey)));
//        assertTrue(ms.contains(eventKey));
//
//        eventDAO.delete(event);
//        assertTrue(ms.contains(filmKey));
//        assertFalse(ms.contains(eventKey));
//    }

    @Test
    public void testKeyToStringRoundTrip() {
        String keyString = eventDAO.fact().keyToString(eventKey);
//        System.out.printf("Key to String: %s%n", keyString);
        Key<Film> roundTripEventKey = eventDAO.fact().stringToKey(keyString);
//        System.out.printf("String to Key: %s%nKey ID: %s%n", key, key.getName());
        assertEquals(eventKey, roundTripEventKey);

    }

    @Test
    public void testDeleteFilm() {
        Event eventByKey = eventDAO.find(eventKey);
        assertNotNull(eventByKey);

        eventDAO.delete(event);
        eventByKey = eventDAO.find(eventKey);
        assertNull(eventByKey);
    }

    @Test
    public void testAddEvent_WithKey() throws ParseException {
        String title = "Annie Hall";
        int year = 1977;
        String directorLast = "Allen";
        Film testFilm = FilmFactory.get().newFilm(title, String.valueOf(year), directorLast);

        Key<Event> testEventKey;// = eventDAO.save(aFilmKey);
        Key<Film> aFilmKey = filmDAO.save(testFilm);
        Key<Film> notAFilmKey = new Key<Film>(Film.class, "not-a-key");

        event.setFilmKey(notAFilmKey);
        testEventKey = eventDAO.save(event);
        assertNull(testEventKey);

        event.setFilmKey(aFilmKey);
        testEventKey = eventDAO.save(event);
        assertNotNull(testEventKey);

        Event foundEvent = eventDAO.find(testEventKey);
        assertNotNull(foundEvent);
        assertEquals(aFilmKey, foundEvent.getFilmKey());

    }

    @Test
    public void testGet() {
        Event getByStringId = eventDAO.find(new Key<Event>(Event.class, "not-a-long-id"));
        assertNull(getByStringId);


        Long id = eventKey.getId();
        Event getByLongId = eventDAO.find(new Key<Event>(Event.class, id));
        assertNotNull(getByLongId);
    }
}
