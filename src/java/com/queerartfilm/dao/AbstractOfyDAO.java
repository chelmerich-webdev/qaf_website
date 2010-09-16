/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.dao;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.helper.DAOBase;
import java.util.List;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public abstract class AbstractOfyDAO<T> extends DAOBase implements GenericDAO<T, Key<T>> {

    protected final Class<T> clazz;
    protected final String sortOrder;

    protected AbstractOfyDAO(Class<T> clazz, String sortOrder) {
        this.clazz = clazz;
        this.sortOrder = sortOrder;
    }

    public Key<T> save(T instance) {
        return ofy().put(instance);
    }

    public void update(T instance) {
        save(instance);
    }

    public void delete(T instance) {
        ofy().delete(instance);
    }

    public T find(Key<T> key) {
        return ofy().find(key);
    }

    // depends on T's id type, String or long
    public abstract T find(String id);
    public abstract T find(long id);
    
    public List<T> find() {
        return Lists.newArrayList(ofy().query(clazz).order(sortOrder).list());
    }


    /**
     * Convenience method to access to all items from a getter via scripting,
     * e.g. ${myDao.allItems}
     *
     * @return a list of all the items
     */
    public List<T> getAllItems() {
        return find();
    }

    /**
     * Convenience method to access all item keys from a getter via scripting,
     * e.g., ${myDao.allItemKeys}
     * @return
     */
    public List<Key<T>> getAllItemKeys() {
        return ofy().query(clazz).order(sortOrder).listKeys();
    }
}
