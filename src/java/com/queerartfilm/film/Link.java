package com.queerartfilm.film;

/**
 * 
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class Link {
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

    
}
