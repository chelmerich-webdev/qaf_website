package com.queerartfilm.validation;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsGreaterThanV extends Validator<String> {

    private static final String message = "must be greater than";

    // use a static method here instead of static variable
    // as we have to pass in a value for the predicate test.
    public static Predicate<String> P(final int value) {
        Predicate<String> Q = new Predicate<String>() {

            public boolean apply(String input) {
                return Integer.parseInt(input) > value;
            }
        };
        return Q = Predicates.and(IsIntegerV.P, Q);
    }
    final int min;

    public IsGreaterThanV(final int min) {
        this(min, String.format("%s %d.", message, min));
    }

    public IsGreaterThanV(int min, String message) {
        super(IsGreaterThanV.P(min), message);
        this.min = min;
    }
}
