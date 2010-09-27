/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.film;

import com.googlecode.objectify.Key;
import com.queerartfilm.dao.FeaturedFilmDAO;
import com.queerartfilm.model.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ch67dotnet
 */
public class FeaturedFilmArchiveServlet extends HttpServlet {

    private static final String DETAIL_VIEW = "/WEB-INF/jsp/archive_feature.jsp";
    private static final String ARCHIVE_VIEW = "/WEB-INF/jsp/archive.jsp";
    private static final String NOT_FOUND_ATTR = "notfound";
    private static final String NOT_FOUND_MSG =
            "The film ID <strong>\"%s\"</strong> was not found.<br/>"
            + "Please look through the %s Archive below.";
    private static final String FILM_ATTR = "ff";
    private static final String ARCHIVE_DIR = "/films";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        FeaturedFilmDAO featureDAO = new FeaturedFilmDAO();
        String key = Utils.getLastUriToken(request);

        if (ARCHIVE_DIR.equals(request.getRequestURI()) || "".equals(key)) {
            request.getRequestDispatcher(ARCHIVE_VIEW).forward(request, response);
        } else {

            FeaturedFilm feature = null;
            if (!"".equals(key)) {
                feature = featureDAO.query().filter("urlKey", key).get();
//                feature = featureDAO.find(new Key<FeaturedFilm>(FeaturedFilm.class, key));
            }

            String view = DETAIL_VIEW;
            if (feature != null) {
                request.setAttribute(FILM_ATTR, feature);
            } else {
                view = ARCHIVE_VIEW;
                request.setAttribute(NOT_FOUND_ATTR, String.format(NOT_FOUND_MSG, key,
                        getServletContext().getInitParameter("com.queerartfilm.wordmark")));
            }
            request.getRequestDispatcher(view).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
    }

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
        processRequest(request, response);
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
