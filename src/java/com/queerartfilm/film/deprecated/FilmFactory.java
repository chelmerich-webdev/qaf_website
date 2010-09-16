/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.film.deprecated;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.queerartfilm.film.Rating;

/**
 * Factory class for generating <code>Film</code> objects.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class FilmFactory {

    private static final FilmFactory INSTANCE = new FilmFactory();

    public static FilmFactory get() {
        return INSTANCE;
    }

    private FilmFactory() {
    }

    public Film newFilm() {
        return new Film();
    }
    public Film newFilm(String title, String year, String directorLast) {
        Integer yearAsInteger = Integer.valueOf(year);
        return new Film(title, yearAsInteger, directorLast);
    }


    public Film newFilm(JSONObject json) throws JSONException {
        String[] req = {"title", "year", "directorLast"};

//
//        if (!(json.has(req[0]) && json.has(req[1]) && json.has(req[2]))) {
//            throw new IllegalArgumentException(
//                    String.format("Must have %s, %s, and %s", req[0], req[1], req[2]));
//        }
        Film result = new Film();
        
        result.setTitle(json.optString("title"));
        result.setYear(json.optInt("year", 1970));
        result.setDirectorFirst(json.optString("directorFirst", ""));
        result.setDirectorLast(json.optString("directorLast", ""));
        result.setMpaaRating(Rating.valueOf(json.optString("mpaaRating", Rating.NR.name())));
        result.setLength(json.optInt("length", 1));

        return result;
    }
}
