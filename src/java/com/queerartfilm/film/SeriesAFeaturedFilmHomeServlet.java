package com.queerartfilm.film;

import com.queerartfilm.dao.FeaturedFilmDAO;
import com.queerartfilm.web.Config;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Homepage servlet. Displays films that are part of the current QAF series and
 * where a film's valid urlKey in the url allows it to be the "selectedFilm".
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class SeriesAFeaturedFilmHomeServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/jsp/series_a.jsp";
    private static final String CONFIG_ATTR = "current";
    private static final String SELECTED_FILM = "selectedFilm";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FeaturedFilmDAO dao = new FeaturedFilmDAO();
        String selectedKey = request.getPathInfo();
        FeaturedFilm selectedFilm = null;

        if (selectedKey != null && selectedKey.length() > 1) {
            // strip off leading slash
            selectedKey = selectedKey.substring(1);
            // query on urlKey field, as id is a long
            selectedFilm = dao.query().filter("urlKey", selectedKey).get();
        }

        // assign default film is none found from url key
        if (selectedFilm == null) {
            Config defaults = (Config) this.getServletContext().getAttribute(CONFIG_ATTR);
            selectedFilm = dao.find(defaults.getFeaturedFilmBKey().getId());
        }

        request.setAttribute(SELECTED_FILM, selectedFilm);
        request.getRequestDispatcher(VIEW).forward(request, response);
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
            throws ServletException,
            IOException {
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
            throws ServletException,
            IOException {
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
