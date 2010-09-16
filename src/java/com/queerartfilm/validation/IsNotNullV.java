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
public class IsNotNullV<T> extends Validator<T> {
    private static final String errorMessage = "A non-null value is required.";
    IsNotNullV() {
        super(Predicates.notNull(), errorMessage);
    }

}
