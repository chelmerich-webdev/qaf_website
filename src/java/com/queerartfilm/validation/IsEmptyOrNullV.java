package com.queerartfilm.validation;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsEmptyOrNullV extends Validator<String> {

    public static final Predicate<String> P;
    private static final String message = "The value must be empty or null.";

    public static Predicate<String> P() {
        return P;
    }

    static {
        P = Predicates.or(Predicates.isNull(), IsEmptyV.P);
    }

    public IsEmptyOrNullV() {
        super(P, message);
    }

    public IsEmptyOrNullV(String message) {
        super(P, message);
    }
}
