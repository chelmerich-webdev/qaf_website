package com.queerartfilm.validation;

import com.google.common.base.Predicate;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsEmptyV extends Validator<String> {

    private static final String message = "Required to be empty.";
    public static final Predicate<String> P;
    public static Predicate<String> P() {
        return P;
    }

    static {
        P = new Predicate<String>() {

            public boolean apply(String input) {
                return "".equals(input);
            }
        };
    }

    public IsEmptyV() {
        super(P, message);
    }
}
