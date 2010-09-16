package com.queerartfilm.validation;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsWithinRangeV extends Validator<String> {

    private static final String message = "must be between ";

    // Use a static method here to create a Predicate<String> from the
    // actual arguments passed in to the Constructor so that the result
    // may be passed back up to the superclass Constructor.
    @SuppressWarnings("unchecked")
    public static Predicate<String> P(int min, int max, boolean inclusiveMin, boolean inclusiveMax) {
        Predicate<String> Q =
                Predicates.and(IsIntegerV.P,
                IsGreaterThanV.P(inclusiveMin ? --min : min),
                IsLessThanV.P(inclusiveMax ? ++max : max));
        return Q;
    }
    final int min;
    final int max;

    /**
     * Constructor
     *
     * @param min beginning of range
     * @param max end of range
     * @param inclusiveMin if true min value included in range, otherwise excluded.
     * @param inclusiveMax if true max value included in range, otherwise excluded.
     */
    public IsWithinRangeV(int min, int max, boolean inclusiveMin, boolean inclusiveMax) {
        this(min, max, inclusiveMin, inclusiveMax,
                message + (inclusiveMin ? "[" : "(") + min + ", " + max + (inclusiveMax ? "]" : ")"));
    }

    public IsWithinRangeV(int min, int max, boolean inclusiveMin, boolean inclusiveMax, String message) {
        super(IsWithinRangeV.P(min, max, inclusiveMin, inclusiveMax), message);
        this.min = min;
        this.max = max;
    }
    /**
     * Convenience constructor specifies both min and max values included in range.
     *
     * @param min beginning of range, inclusive of min
     * @param max end of range, incluse of max
     */
    public IsWithinRangeV(int min, int max) {
        this(min, max, true, true);
    }

        public IsWithinRangeV(int min, int max, String message) {
        this(min, max, true, true, message);
    }
}
