package com.queerartfilm.film;

import org.json.JSONObject;
import org.json.JSONString;


/**
 * Models MPAA film rating system
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public enum Rating implements JSONString {

    NR, 
    G,
    PG,
    PG13() {
        @Override public String toString() { return "PG-13"; }
    },
    R,
    NC17() {
        @Override public String toString() { return "NC-17"; }
    },
    X;

    public String getName() {
        return name();
    }

    @Override
    public String toJSONString() {
        String result = null;
        JSONObject json = new JSONObject();
        try {
            json.put("name", this. name());
            result = json.toString();
        } catch (Exception e) {
        }
        return result;
    }
}
