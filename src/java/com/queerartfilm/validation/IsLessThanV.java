/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.validation;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsLessThanV extends Validator<String> {

    // use a static method here instead of static variable
    // as we have to pass in a value for the predicate test.
    public static Predicate<String> P(final int value) {
        Predicate<String> Q = new Predicate<String>() {
            public boolean apply(String input) {
                return Integer.parseInt(input) < value;
            }
        };
        return Q;
    }
    private static final String message = "Must be greater than";

    final int max;

    IsLessThanV(final int max) {
        super(Predicates.and(IsIntegerV.P, IsLessThanV.P(max)), 
              String.format("%s %d.", message, max));
        this.max = max;
    }
}
