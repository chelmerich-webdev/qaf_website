/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.web;

import com.queerartfilm.dao.ConfigDAO;
import com.googlecode.objectify.Key;
import com.queerartfilm.film.FeaturedFilm;
import com.queerartfilm.series.QAFSeries;
import com.queerartfilm.validation.FormUtil;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ch67dotnet
 */
public class ConfigUpdateServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ConfigUpdateServlet.class.getName());
    private final String view = "/WEB-INF/jsp/update_config.jsp";
//    private static final String FIELD_SERIES = "series";
//    private static final String FIELD_EVENT = "event";
    private static final String FIELD_QAF_SERIES_KEY = "qafSeriesKey";
    private static final String FIELD_FEATURED_FILM_KEY = "featuredFilmKey";
    private static final String FIELD_YEAR = "year";
    private static final String FIELD_FACEBOOK_URL = "facebookUrl";
    private static final String FIELD_EMAIL_ADDRESS = "subscribeEmailAddress";
    private static final String FIELD_EMAIL_SUBJECT = "subscribeEmailSubject";
    private static final String FIELD_EMAIL_BODY = "subscribeEmailBody";
    private static final String ATTR_CURRENT_CONFIG = "current";

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String qafSeriesId = FormUtil.getParamOrEmpty(request, FIELD_QAF_SERIES_KEY);
        String featureId = FormUtil.getParamOrEmpty(request, FIELD_FEATURED_FILM_KEY);
        String year = FormUtil.getParamOrEmpty(request, FIELD_YEAR);
        String facebookUrl = FormUtil.getParamOrEmpty(request, FIELD_FACEBOOK_URL);
        String emailAddress = FormUtil.getParamOrEmpty(request, FIELD_EMAIL_ADDRESS);
        String emailSubject = FormUtil.getParamOrEmpty(request, FIELD_EMAIL_SUBJECT);
        String emailBody = FormUtil.getParamOrEmpty(request, FIELD_EMAIL_BODY);
        
        ServletContext context = this.getServletContext();
        ConfigDAO configDAO = new ConfigDAO();
        Config currentConfig = (Config) context.getAttribute(ATTR_CURRENT_CONFIG);

        if (!"".equals(qafSeriesId)) {
            long id;
            try {
                id = Long.parseLong(qafSeriesId);
                currentConfig.setQafSeriesKey(new Key<QAFSeries>(QAFSeries.class, id));
            } catch (NumberFormatException ex) {
                logger.warning(ex.getClass().getName() + " error for " + qafSeriesId);
            }
        }
        if (!"".equals(featureId)) {
            long id;
            try {
                id = Long.parseLong(featureId);
                currentConfig.setFeaturedFilmKey(new Key<FeaturedFilm>(FeaturedFilm.class, id));
            } catch (NumberFormatException ex) {
                logger.warning(ex.getClass().getName() + " error for " + featureId);
            }
        }

        if (!"".equals(year)) {
            currentConfig.setYear(year);
        }

        if (!"".equals(facebookUrl)) {
            currentConfig.setFacebookUrl(facebookUrl);
        }

        if (!"".equals(emailAddress)) {
            currentConfig.setSubscribeEmailAddress(emailAddress);
        }

        if (!"".equals(emailSubject)) {
            currentConfig.setSubscribeEmailSubject(emailSubject);
        }

        if (!"".equals(emailBody)) {
            currentConfig.setSubscribeEmailBody(emailBody);
        }
        
        configDAO.save(currentConfig);
        this.getServletContext().setAttribute(ATTR_CURRENT_CONFIG, currentConfig);
        logger.info("Config set: " + currentConfig);
        request.setAttribute("message", "Website Configuration was updated.");
        request.getRequestDispatcher("/manage").forward(request, response);

    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            request.getRequestDispatcher(view).forward(request, response);
    }


    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
