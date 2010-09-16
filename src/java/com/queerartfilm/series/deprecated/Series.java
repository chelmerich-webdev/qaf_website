package com.queerartfilm.series.deprecated;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.queerartfilm.event.deprecated.Event;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import javax.persistence.Id;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Cached
public class Series implements Comparable<Series> {

    private static final Logger logger = Logger.getLogger(Series.class.getName());
    @Id Long id;
    private String title;
    private SortedSet<Key<Event>> events;

    public Series() {
        this.title = "";
        this.events = new TreeSet<Key<Event>>();
    }
    public Series(JSONObject json) throws JSONException {
        this();
        this.setTitle(json.optString("title", "Initialization Series"));
        this.addEventsElement(json.getString("eventKey"));

    }

    public List<Key<Event>> getEvents() {
        List<Key<Event>> list = new ArrayList<Key<Event>>(events);
        // return descending order
        Collections.reverse(list);
        return list;
    }

    public void setEvents(Collection<Key<Event>> events) {
        this.events = new TreeSet<Key<Event>>(events);
    }

    public void addEventsElement(long id) {
        events.add(new Key<Event>(Event.class, id));
    }

    public void addEventsElement(String eventIdAsString) {
        long eventId;
        try {
            eventId = Long.parseLong(eventIdAsString);
        } catch (NumberFormatException ex) {
            logger.warning(ex.getClass().getName() + " on Event id parse in Series");
            return;
        }
        events.add(new Key<Event>(Event.class, eventId));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Convenience method that returns latest event id for use in query sorting
     * @return latest event id
     */
    public long getLatestEventId() {
        return events.last().getId();
    }

    public int compareTo(Series that) {
        if (this.events.isEmpty()) {
            if (that.events.isEmpty()) {
                return 0;
            } else {
                return -1;
            }
        }

        // this.events is shown to not be empty
        if (that.events.isEmpty()) {
            return 1;
        }

        // this.events and that.events are now know to not be empty,
        // so go ahead and make comparison of the latest events
        return this.events.last().compareTo(that.events.last());
    }

    @Override
    public String toString() {
        return title;
    }
}
