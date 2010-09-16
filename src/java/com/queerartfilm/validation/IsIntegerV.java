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
public class IsIntegerV extends Validator<String> {

    public static final Predicate<String> P;
    private static final String integerRegex = "-?\\d+";
    private static final String errorFormat = "%s is invalid, it should be an integer.";

    public static boolean isInteger(String input) {
        return P.apply(input);
    }
    
    static {
        Predicate<String> protoP =
                new Predicate<String>() {
                    public boolean apply(String input) {
                        return input.matches(integerRegex);
                    }
                };
        P = Predicates.and(Predicates.notNull(), protoP);
    }

    IsIntegerV() {
        super(P, errorFormat);
    }
}
