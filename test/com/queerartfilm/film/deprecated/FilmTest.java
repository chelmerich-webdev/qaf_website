/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.film.deprecated;

import com.queerartfilm.film.deprecated.Film;
import com.queerartfilm.film.deprecated.FilmFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.queerartfilm.dao.deprecated.FilmDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ch67dotnet
 */
public class FilmTest {

    Film film;
    Key<Film> key;
    FilmDAO dao;
    private final LocalServiceTestHelper helper =
                        new LocalServiceTestHelper(
                                        new LocalDatastoreServiceTestConfig(),
                                        new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        film = FilmFactory.get().newFilm("Star Wars", "1977", "Lucas");
        dao = new FilmDAO();
        key = dao.save(film);
    }

    @After
    public void tearDown() {
        helper.tearDown();
        film = null;
        dao= null;
        key = null;
    }

    @Test
    public void testEquals() {
        Film gotFilm = dao.find(key);
        assertEquals(film, gotFilm);
    }

}
