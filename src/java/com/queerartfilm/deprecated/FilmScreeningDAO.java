/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.deprecated;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

/**
 * Data Access Object for the QAF datastore
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
@Deprecated // first of three iterations, this one using JDO and the first data scheme
public class FilmScreeningDAO {
    private static final FilmScreeningDAO daoInstance = new FilmScreeningDAO();

    private FilmScreeningDAO() {

    }
    
    public static FilmScreeningDAO get() {
        return daoInstance;
    }

    public void addFilm(FilmScreening film) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(film);
        } finally {
            pm.close();
        }
    }

    public FilmScreening getFilm(String id) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        return pm.getObjectById(FilmScreening.class, id);

    }

    public List<FilmScreening> getAllFilms() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Extent<FilmScreening> extent = pm.getExtent(FilmScreening.class);

        List<FilmScreening> films = new ArrayList<FilmScreening>();
        for (FilmScreening film : extent) {
            films.add(film);
        }
        
        extent.closeAll();
        return films;
    }
}
