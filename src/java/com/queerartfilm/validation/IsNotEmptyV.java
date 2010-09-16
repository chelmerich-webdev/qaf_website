package com.queerartfilm.validation;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsNotEmptyV extends Validator<String> {

    public static final Predicate<String> P;
    private static final String message = "A value is required.";

    static {
        P = Predicates.not(IsEmptyV.P);
    }

    public IsNotEmptyV() {
        super(P, message);
    }

    public IsNotEmptyV(String message) {
        super(P, message);
    }
}
