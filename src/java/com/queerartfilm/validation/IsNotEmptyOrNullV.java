package com.queerartfilm.validation;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsNotEmptyOrNullV extends Validator<String> {

    public static final Predicate<String> P;
    private static final String message = "a value is required";

    public static Predicate<String> P() {
        return P;
    }

    static {
        Predicate<String> Q = Predicates.or(Predicates.isNull(), IsEmptyV.P);
        P = Predicates.not(Q);
    }

    public IsNotEmptyOrNullV() {
        super(P, message);
    }

    public IsNotEmptyOrNullV(String message) {
        super(P, message);
    }
}
