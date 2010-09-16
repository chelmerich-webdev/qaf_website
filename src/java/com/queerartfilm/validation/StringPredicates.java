package com.queerartfilm.validation;

import com.google.common.base.Predicate;

/**
 *
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Deprecated
public enum StringPredicates implements Predicate<String>, Validateable<String> {

    IS_EMPTY(IsEmptyV.P),
    IS_INTEGER(IsIntegerV.P),
    ;
    private Predicate<String> p;
    private Validator<String> v;

    private StringPredicates(Predicate<String> p) {
        this.p = p;
        this.v = new Validator<String>(p);
    }

    public boolean apply(String input) {
        return this.apply(input);
    }

    public boolean validate(String input) throws ValidatorException {
        return v.validate(input);
    }
}
