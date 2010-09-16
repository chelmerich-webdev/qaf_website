/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public interface GenericDAO<T, K extends Serializable> {

    T find(K key);
    
//    T find(T instance);

    List<T> find();


//    List<T> find(String queryName, String[] paramNames, Object[] bindValues);

    K save(T instance);

    void update(T instance);

    void delete(T instance);
}
