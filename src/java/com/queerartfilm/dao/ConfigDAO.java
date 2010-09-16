/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.queerartfilm.web.Config;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class ConfigDAO extends AbstractOfyDAO<Config> {
    private static final Key<Config> CONFIG_KEY =
            new Key<Config>(Config.class, Config.ID);
    static {
        ObjectifyService.register(Config.class);
    }
    
    public ConfigDAO() {
        super(Config.class, "year");
    }

    public Config findConfig() {
        return ofy().find(CONFIG_KEY);
    }

    public Config getConfig() {
        return ofy().find(CONFIG_KEY);
    }
    @Override
    public Config find(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Config find(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
