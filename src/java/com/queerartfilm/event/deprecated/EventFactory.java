package com.queerartfilm.event.deprecated;

import com.queerartfilm.film.Venue;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.queerartfilm.film.deprecated.Film;
import com.googlecode.objectify.Key;
import java.util.Date;
import static com.queerartfilm.validation.IsDateP.*;

/**
 * Factory class for generating <code>Event</code> objects.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class EventFactory {

    private static final EventFactory INSTANCE = new EventFactory();

    public static EventFactory get() {
        return INSTANCE;
    }

    private EventFactory() {
    }

    public Event newEvent(JSONObject json) throws JSONException {
        Event result = new Event();
        Date now = new Date();
        result.setFilmKey(new Key<Film>(Film.class, json.getString("filmKey")));
        result.setId(now.getTime());
        result.setYear(json.optString("year", dfYear.format(now)));
        result.setMonth(json.optString("month", dfMonth.format(now)));
        result.setDay(json.optString("day", dfDay.format(now)));
        result.setHour(json.optString("hour", dfHour.format(now)));
        result.setMinute(json.optString("minute", dfMinute.format(now)));
        result.setAmpm(json.optString("ampm", dfAmpm.format(now)));
        result.setPresenterFirst(json.optString("presenterFirst", ""));
        result.setPresenterLast(json.optString("presenterLast", ""));
        result.setSynopsis(json.optString("synopsis", "Initialization Screening - Ok to delete."));
        result.setVenue(Venue.valueOf(json.optString("venue", Venue.IFC_CENTER.name())));

        return result;
    }

    public Event newEvent() {
        return new Event();
        
    }
    public Event newEvent(Key<Film> filmKey) {
        if(filmKey == null) {
            return null;
        }
        return new Event(filmKey);
    }
}
