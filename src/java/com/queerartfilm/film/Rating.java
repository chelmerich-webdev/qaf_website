package com.queerartfilm.film;

/**
 * Models MPAA film rating system
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public enum Rating {

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
}
