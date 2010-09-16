/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.deprecated;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

/**
 * Factory class for original data model for main entity, <code>FilmScreening</code>,
 * which was replaced by <code>QafSeriesEvent</code>.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Deprecated
public class FilmScreeningFactory {
    public static final SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy h:mm a");

    public static FilmScreening newFilmScreening(String filmAsSingleString,String delimiter) {
        List<String> tokens = new ArrayList<String>();
        for (StringTokenizer t = new StringTokenizer(delimiter); t.hasMoreTokens(); ) {
            tokens.add(t.nextToken());
        }
        String[] f = new String[12];
        f = tokens.toArray(f);
        return newFilmScreening(f[0],f[1],f[2],f[3],f[4],f[5],f[6],f[7],f[8],f[9],f[10],f[11]);
    }
    public static FilmScreening newFilmScreening(String title, String year,
            String directorFirst, String directorLast, String synopsis,
            String date, String venue, String venueAddr1, String venueAddr2,
            String venuePhoneNumber, String presenterFirst, String presenterLast)
         {

//        // create key
//        Key key = KeyFactory.createKey(
//                FilmScreening.class.getSimpleName(), createID(title));
        FilmScreening film = new FilmScreening();
//        film.setKey(key);
        film.setTitle(title);
        try {
            film.setYear(Integer.parseInt(year));
        } catch (NumberFormatException ex) {
            System.out.println("Trying to parse: " + year);
        }
        film.setDirectorFirst(directorFirst);
        film.setDirectorLast(directorLast);
        film.setSynopsis(synopsis);
        try {
        film.setScreeningDateAndTime(format.parse(date));
        } catch (ParseException ex) {
            System.out.println("Trying to parse: " + date);
        }
        film.setScreeningVenue(venue);
        film.setVenueAddress1(venueAddr1);
        film.setVenueAddress2(venueAddr2);
        film.setVenuePhoneNumber(venuePhoneNumber);
        film.setPresenterFirst(presenterFirst);
        film.setPresenterLast(presenterLast);
        return film;
    }

    public static FilmScreening newFilm(HttpServletRequest request) {

        // create screening date and time from atomic values
//        Calendar screeningDateAndTime = Calendar.getInstance();
//        screeningDateAndTime.set(Calendar.YEAR,
//                Integer.parseInt(request.getParameter("screeningYear")));
//        screeningDateAndTime.set(Calendar.MONTH,
//                Integer.parseInt(request.getParameter("screeningMonth")));
//        screeningDateAndTime.set(Calendar.DATE,
//                Integer.parseInt(request.getParameter("screeningDay")));
//        screeningDateAndTime.set(Calendar.HOUR,
//                Integer.parseInt(request.getParameter("screeningHour")));
//        screeningDateAndTime.set(Calendar.MINUTE, 0);
//        screeningDateAndTime.set(Calendar.SECOND, 0);
//        screeningDateAndTime.set(Calendar.AM_PM, Calendar.PM);


        // create key
        Key key = KeyFactory.createKey(
                FilmScreening.class.getSimpleName(),
                createID(request.getParameter("title")));

        FilmScreening film = new FilmScreening();
        film.setKey(key);
        film.setTitle(request.getParameter("title"));
        film.setYear(Integer.parseInt(request.getParameter("year")));
        film.setSynopsis(request.getParameter("synopsis"));
        film.setDirectorFirst(request.getParameter("directorFirst"));
        film.setDirectorLast(request.getParameter("directorLast"));

        film.setSeries(request.getParameter("series"));
        film.setScreeningDateAndTime(request.getParameter("screeningYear"),
                request.getParameter("screeningMonth"),
                request.getParameter("screeningDate"),
                request.getParameter("screeningHour"),
                request.getParameter("screeningMinute"));
        film.setScreeningVenue(request.getParameter("screeningVenue"));
        film.setVenueAddress1(request.getParameter("venueAddress1"));
        film.setVenueAddress2(request.getParameter("venueAddress2"));
        film.setVenuePhoneNumber(request.getParameter("venuePhoneNumber"));
        film.setPresenterFirst(request.getParameter("presenterFirst"));
        film.setPresenterLast(request.getParameter("presenterLast"));

        return film;
    }

    /**
     * Converts a string to and single lowercase token id by
     * preserving all letters, digits, and single hyphens and substituting
     * hyphens for spaces. All other characters are skipped.
     * Applying this method to a conforming id returns the same value.
     * @param title
     * @return
     */
    public static String createID(String title) {
        char dash = '-';
        boolean isEmpty = true;
        StringBuilder sb = new StringBuilder();
        // loop through each token
        for (StringTokenizer st = new StringTokenizer(title.trim().toLowerCase()); st.hasMoreTokens();) {
            char prev = dash;
            isEmpty = true;

            // add each letter and digit, or a dash if previous was not a dash
            for (char c : st.nextToken().toCharArray()) {
                if (Character.isLetterOrDigit(c) || (c == dash && prev != dash)) {
                    sb.append(c);
                    prev = c;
                    isEmpty = false;
                }
            }
            if (isEmpty) {
                continue;
            }
            sb.append(dash);
        }
        String result = sb.toString().trim();
        return (result.length() > 0 ? result.substring(0, result.length() - 1) : "");  // remove trailing hyphen

    }
}
