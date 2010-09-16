package com.queerartfilm.validation;

import com.google.common.base.Predicate;

/**
 *
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class Validator<T> implements Validateable<T> {

    private final Predicate<? super T> predicate;
    private String message = "Invalid: " + this.getClass().getName();

    protected Validator(Predicate<? super T> predicate) {
        this.predicate = predicate;
    }

    protected Validator(Predicate<? super T> predicate, String messsage) {
        this.predicate = predicate;
        this.message = messsage;
    }

    public boolean validate(T input) throws ValidatorException {
        if (predicate.apply(input)) {
            return true;
        } else {
            throw new ValidatorException(message);
        }
    }
    
    public Predicate<? super T> getPredicate() {
        return predicate;
    }

//    public void setMessage(String message) {
//        this.msgFormat = message;
//    }
}
