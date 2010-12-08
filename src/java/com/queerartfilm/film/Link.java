package com.queerartfilm.film;

import com.google.appengine.repackaged.org.json.JSONObject;
import com.google.appengine.repackaged.org.json.JSONString;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class Link implements JSONString {
    private static final String YOU_TUBE_ID_REGEX = "[a-zA-Z0-9_-]{11}";
    private String label;
    private String url;

    public Link(String label, String url) {
        this.label = label;
        this.url = url;
    }
    public Link() {
        this("", "");
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isYouTubeId() {
        return Pattern.matches(YOU_TUBE_ID_REGEX, this.url);
    }

    @Override
    public String toJSONString() {
        String result = null;
        JSONObject json = new JSONObject();
        try {
            json.put("label", this.getLabel());
            json.put("url", this.getUrl());
            json.put("youTubeId", this.isYouTubeId());
            result = json.toString();
        } catch (Exception e) {
        }
        return result;
    }


}
