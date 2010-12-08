package com.queerartfilm.film;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.appengine.repackaged.org.json.JSONString;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java Bean transfer object for a person
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class Person implements Serializable, JSONString {

    private String first;
    private String last;

    public Person(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public Person () {
        this("", "");
    }


    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        if ("".equals(first)) {
            return last;
        }
        return String.format("%s %s", first, last);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if ((this.first == null) ? (other.first != null) : !this.first.equals(other.first)) {
            return false;
        }
        if ((this.last == null) ? (other.last != null) : !this.last.equals(other.last)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.first != null ? this.first.hashCode() : 0);
        hash = 71 * hash + (this.last != null ? this.last.hashCode() : 0);
        return hash;
    }

    @Override
    public String toJSONString() {
        JSONObject json = new JSONObject();
        String result = null;
        try {
            json.put("first", this.getFirst());
            json.put("last", this.getLast());
            result = json.toString();
        } catch (JSONException ex) { }
        
        return result;
    }

}
