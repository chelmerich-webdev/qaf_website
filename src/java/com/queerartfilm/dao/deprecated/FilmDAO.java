package com.queerartfilm.dao.deprecated;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.queerartfilm.dao.AbstractOfyDAO;
import com.queerartfilm.film.deprecated.Film;
import com.queerartfilm.model.Utils;
import java.util.List;
import java.util.logging.Logger;

/**
 * Objectify DAO for Film entities
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class FilmDAO extends AbstractOfyDAO<Film> {

    private static final String SORT_ORDER = "id";
    private static final Logger logger = Logger.getLogger(FilmDAO.class.getName());
    private static final FilmDAO DAO = new FilmDAO();
    /**
     * Custom EL Function, spec'ed in the qaf.tld
     *
     * @param id
     * @return the Film that matches this id
     */
    public static Film getFilmByKey(Key<Film> key) {
        return DAO.find(key);
    }

    static {
        ObjectifyService.register(Film.class);
    }

    public FilmDAO() {
        super(Film.class, SORT_ORDER);
    }

    @Override
    public Key<Film> save(Film film) {
        if ("".equals(film.getId())) {
            film.setId(Utils.createID(film.getTitle()));
        }
        return super.save(film);
    }

//    public Film find(Film instance) {
//        if (instance == null) {
//            throw new IllegalArgumentException();
//        }
//        return ofy().find(clazz, instance.getId());
//    }

    public Film find(String id) {
        return ofy().find(clazz, id);
    }

    public Film find(long id) {
        throw new UnsupportedOperationException("Film ID is a String not a long!");
    }


    public List<Film> getUnassigned() {
        Query<Film> q = ofy().query();
        return q.filter("assigned", false).order(SORT_ORDER).list();
    }
}
