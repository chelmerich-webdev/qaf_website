/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.dao.deprecated;

import com.queerartfilm.film.deprecated.FilmFactory;
import com.queerartfilm.dao.deprecated.FilmDAO;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.queerartfilm.film.Rating;
import com.queerartfilm.film.deprecated.Film;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ch67dotnet
 */
public class FilmDAOTest {

    FilmDAO dao;
    Film film;
    Key<Film> filmKey;
    private final LocalServiceTestHelper helper =
                        new LocalServiceTestHelper(
                                        new LocalDatastoreServiceTestConfig(),
                                        new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        dao = new FilmDAO();
        film = new Film("Star Wars", 1977, "Lucas", "George", Rating.G, 120);
        filmKey = dao.save(film);
    }

    @After
    public void tearDown() {
        helper.tearDown();
        dao = null;
        film = null;
        filmKey = null;
    }

    @Test
    public void testMemcache() {
        MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
        assertEquals(1, ms.getStatistics().getItemCount());
        Film anotherFilm = FilmFactory.get().newFilm("The Empire Strikes Back", "1980", "Kershner");
        dao.save(anotherFilm);
        assertEquals(ms.getStatistics().getItemCount(), 2, ms.getStatistics().getItemCount());

        // Can't see to determine what the key is for the memcache. Should be rawkey
//        com.google.appengine.api.datastore.Key rawkey = KeyFactory.createKey("Film", "star-wars");
//        System.out.printf("Get Film Key: %s%n", ms.get(filmKey));
//        System.out.printf("Get Film Key: %s%n", ms.get(dao.fact().keyToString(filmKey)));
//        System.out.printf("Get Film Key: %s%n", ms.get(rawkey));
        assertEquals(2, ms.getStatistics().getItemCount());
//        assertTrue(ms.contains(dao.fact().keyToString(filmKey)));
        dao.delete(film);
        assertEquals(1, ms.getStatistics().getItemCount());

    }

    @Test
    public void testKeyToStringRoundTrip() {
        String keyString = dao.fact().keyToString(filmKey);
//        System.out.printf("Key to String: %s%n", keyString);
        Key<Film> roundTripFilmKey = dao.fact().stringToKey(keyString);
//        System.out.printf("String to Key: %s%nKey ID: %s%n", key, key.getName());
        assertEquals(filmKey, roundTripFilmKey);

    }

    @Test
    public void testDeleteFilm() {
        Film filmByKey = dao.find(filmKey);
        assertNotNull(filmByKey);

        dao.delete(film);
        filmByKey = dao.find(filmKey);
        assertNull(filmByKey);
    }
    @Test
    public void testAddFilm_3Strings() {
        String title = "The Return of the Jedi";
        int year = 1984;
        String directorLast = "Lucas";
        Key<Film> addedFilmKey = dao.save(FilmFactory.get().newFilm(title, String.valueOf(year), directorLast));

        Film foundFilm = dao.find(addedFilmKey);
        assertNotNull(foundFilm);
        assertEquals(title, foundFilm.getTitle());
        assertTrue(year == foundFilm.getYear());
        assertEquals(directorLast, foundFilm.getDirectorLast());
    }


    @Test
    public void testGet() {
        String id = filmKey.getName();
        Film fetched = dao.find(new Key<Film>(Film.class, id));
        assertTrue(
                String.format("film: %s%nfetched: %s%n", film, fetched),
                film.equals(fetched));
        
        fetched = dao.find(new Key<Film>(Film.class, "not-an-id"));
        assertNull(fetched);

        Film getByLongId = dao.find(new Key<Film>(Film.class, 1L));
        assertNull(getByLongId);
    }
}
