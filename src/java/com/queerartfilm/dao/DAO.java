/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.dao;

import com.googlecode.objectify.Key;

/**
 *
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public interface DAO<E> {
    E find(long id);
    Key<E> update(E e);
    void delete(E e);
}
