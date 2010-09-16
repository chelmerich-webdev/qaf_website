package com.queerartfilm.film.deprecated;

import com.googlecode.objectify.Key;
import com.queerartfilm.dao.deprecated.FilmDAO;
import com.queerartfilm.validation.FormUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ch67dotnet
 */
public class UpdateFilmServlet extends HttpServlet {
    // Constants ----------------------------------------------------------------------------------

    private static final String VIEW = "/WEB-INF/jsp/update_film.jsp";
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_FILM = "film";
    private static final String PARAM_ID = "id";
    // Vars ---------------------------------------------------------------------------------------
    private FilmDAO filmDAO;

    // HttpServlet actions ------------------------------------------------------------------------
    @Override
    public void init() throws ServletException {
        // Obtain the FilmDAODeprecated from DAOFactory by Config.
//        this.filmDAO = Config.getInstance(getServletContext()).getDAOFactory().getFilmDAO();
        this.filmDAO = new FilmDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show view.
        String id = FormUtil.getParamOrEmpty(request, PARAM_ID);
        Film film = null;
        if (!("".equals(id) || "new".equals(id))) {
            film = filmDAO.find(new Key<Film>(Film.class, id));
        }
        if (film == null) { // we haven't got a valid film id or didn't have one to start with
            film = FilmFactory.get().newFilm();
        }
        request.setAttribute(ATTRIBUTE_FILM, film);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prepare form bean. Get a new form bean each time to validate the
        // current request. values will be set on the form
        FilmFormV filmForm = new FilmFormV(filmDAO);
        request.setAttribute(ATTRIBUTE_FORM, filmForm);

        // Process request and get result.
        Film film = filmForm.validateFilm(request);
        request.setAttribute(ATTRIBUTE_FILM, film);

        // Postback.
        request.getRequestDispatcher(VIEW).forward(request, response);
    }
}
