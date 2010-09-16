/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.validation;

import com.queerartfilm.validation.ValidatorException;

/**
 *
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public interface Validateable<T> {
    public boolean validate(T input) throws ValidatorException;
}
