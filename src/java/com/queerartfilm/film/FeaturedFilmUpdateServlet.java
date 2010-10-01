package com.queerartfilm.film;

import com.queerartfilm.dao.FeaturedFilmDAO;
import com.queerartfilm.validation.FormUtil;
import com.queerartfilm.validation.IsIntegerV;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
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

    private static final Logger logger = Logger.getLogger(FeaturedFilmUpdateServlet.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/update_feature.jsp";
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_FILM = "feature";
    private static final String PARAM_ID = "id";
    private static final List<String> MONTHS = Arrays.asList(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December");
    // Enum values aren't available to EL, so make available as attributes
    private static final List<Rating> RATINGS = Arrays.asList(Rating.values());
    private static final List<Venue> VENUES = Arrays.asList(Venue.values());
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
//        logger.info("In FF Update doGet()");
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
        // Add constant values as attributes to facilitate EL access
        request.setAttribute("monthList", MONTHS);
        request.setAttribute("ratingsList", RATINGS);
        request.setAttribute("venueList", VENUES);
        request.setAttribute(ATTRIBUTE_FILM, feature);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        logger.info("In FF Update doPost()");

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
