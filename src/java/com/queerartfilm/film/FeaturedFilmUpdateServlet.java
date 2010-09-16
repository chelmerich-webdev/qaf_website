package com.queerartfilm.film;

import com.queerartfilm.dao.FeaturedFilmDAO;
import com.queerartfilm.validation.FormUtil;
import com.queerartfilm.validation.IsIntegerV;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ch67dotnet
 */
public class FeaturedFilmUpdateServlet extends HttpServlet {
    // Constants ----------------------------------------------------------------------------------

    private static final String VIEW = "/WEB-INF/jsp/update_feature.jsp";
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_FILM = "feature";
    private static final String PARAM_ID = "id";
    // Vars ---------------------------------------------------------------------------------------
    private FeaturedFilmDAO featureDAO;

    // HttpServlet actions ------------------------------------------------------------------------
    @Override
    public void init() throws ServletException {
        // Obtain the FilmDAODeprecated from DAOFactory by Config.
//        this.filmDAO = Config.getInstance(getServletContext()).getDAOFactory().getFilmDAO();
        this.featureDAO = new FeaturedFilmDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show view.
        String id = FormUtil.getParamOrEmpty(request, PARAM_ID);
        FeaturedFilm feature = null;
        if (IsIntegerV.P.apply(id)) {
            feature = featureDAO.find(Long.parseLong(id));
        }

//        QAFSeries series = null;
//        if (!"".equals(id) && IsIntegerV.P.apply(id)) {
//            series = seriesDAO.find(Long.parseLong(id));
//        }

        if (feature == null) { // we haven't got a valid film id or didn't have one to start with
            feature = new FeaturedFilm();
        }
        request.setAttribute(ATTRIBUTE_FILM, feature);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prepare form bean. Get a new form bean each time to validate the
        // current request. values will be set on the form
        FeaturedFilmFormV featureForm = new FeaturedFilmFormV(featureDAO);
        request.setAttribute(ATTRIBUTE_FORM, featureForm);

        // Process request and get result.
        FeaturedFilm feature = featureForm.validateFeaturedFilm(request);
        request.setAttribute(ATTRIBUTE_FILM, feature);

        // Postback.
        request.getRequestDispatcher(VIEW).forward(request, response);
    }
}
