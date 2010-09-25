package com.queerartfilm.film;

import java.util.regex.Pattern;

/**
 * 
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class Link {
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
    
}
