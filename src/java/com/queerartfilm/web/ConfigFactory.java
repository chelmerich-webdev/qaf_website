/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.queerartfilm.web;

import com.queerartfilm.dao.ConfigDAO;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class ConfigFactory {

    private static final ConfigFactory INSTANCE = new ConfigFactory();
    public static ConfigFactory get() {
        return INSTANCE;
    }
    private ConfigFactory() {

    }

    public Config currentConfig() {
        ConfigDAO dao = new ConfigDAO();
        Config result = dao.findConfig();
        if (result == null) {

        }
        return result;

    }

}
