/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.film.deprecated;

import com.queerartfilm.film.deprecated.Film;
import com.queerartfilm.film.deprecated.FilmFactory;
import com.google.appengine.repackaged.org.json.CDL;
import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.appengine.repackaged.org.json.JSONTokener;
import com.queerartfilm.deprecated.QafSeriesEventJDO.Film;
import com.queerartfilm.event.deprecated.Event;
import com.queerartfilm.event.deprecated.EventFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ch67dotnet
 */
public class FilmFactoryTest {

    String fieldnames = "title,year,directorLast,directorFirst,mpaaRating,length";
    String f0, f1, f2, f3, f4, f5, starwars;
    FilmFactory factory;
    JSONArray columns;
    @Before
    public void setUp() throws JSONException {
        factory = FilmFactory.get();
        f0 = "Star Wars";
        f1 = "1977";
        f2 = "Lucas";
        f3 = "George";
        f4 = "G";
        f5 = "120";
        starwars = String.format("%s,%s,%s,%s,%s,%s",f0,f1,f2,f3,f4,f5);
        JSONTokener tokener = new JSONTokener(fieldnames);
        columns = CDL.rowToJSONArray(tokener);
    }

    @Test
    public void testOptionalValues() throws JSONException {
        Film film = factory.newFilm(new JSONObject("{title: not a film}"));
        System.out.println(film.toJSONString());
        Event event = EventFactory.get().newEvent(new JSONObject("{filmKey: init}"));
        System.out.println(event.toJSONString());
    }
    @Test
    public void testFilmFactoryJSONArgThrowsException() throws JSONException {
        String incompleteInfo = String.format("%s,%s,%s,%s,%s", f1,f2,f3,f4,f5);
        JSONObject incompleteFilm = CDL.rowToJSONObject(columns, new JSONTokener(incompleteInfo));
        Film film = null;
        try {
            film = factory.newFilm(incompleteFilm);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
        incompleteInfo = String.format("%s,%s,%s,%s,%s", f0,f2,f3,f4,f5);
        try {
            film = factory.newFilm(incompleteFilm);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
        incompleteInfo = String.format("%s,%s,%s,%s,%s", f0,f1,f3,f4,f5);
        try {
            film = factory.newFilm(incompleteFilm);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testFilmFactoryWithJSONArg() throws JSONException  {
        
        JSONObject jsonFilm = CDL.rowToJSONObject(columns, new JSONTokener(starwars));
        Film film = factory.newFilm(jsonFilm);

        assertNotNull(film);
        assertEquals(f0, film.getTitle());
        assertTrue(Integer.parseInt(f1) == film.getYear());
        assertEquals(f2, film.getDirectorLast());
        assertEquals(f3, film.getDirectorFirst());
        assertEquals(f4, film.getMpaaRating().toString());
        assertEquals(f5, film.getLength().toString());
        System.out.println(jsonFilm.toString(4));
    }
    @Test
    public void testJSONorgAPI() throws JSONException {
        JSONTokener tokener = new JSONTokener(fieldnames);
//        System.out.println(tokener.toString());
        assertNotNull(tokener);
        JSONArray testArray = new JSONArray();
        assertNotNull(testArray);
//        System.out.printf("testArray: %s%n", testArray.toString());
        JSONArray columns = CDL.rowToJSONArray(tokener);
        assertNotNull(columns);
//        System.out.printf("columns: %s%n", columns.toString());
        JSONObject jsonFilm = CDL.rowToJSONObject(columns, new JSONTokener(starwars));
        assertNotNull(jsonFilm);
//        System.out.printf("testFilm in JSON: %s%n", jsonFilm.toString());

    }

}