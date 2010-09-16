/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.validation;

import com.google.common.base.Predicates;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class IsNullV<T> extends Validator<T> {
    private static final String errorMessag = "Invalid because of a null value.";
    IsNullV() {
        super(Predicates.isNull(), errorMessag);
    }

}
