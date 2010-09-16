/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.film;

import com.queerartfilm.dao.FeaturedFilmDAO;
import com.queerartfilm.model.Utils;
import com.queerartfilm.web.Config;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ch67dotnet
 */
public class FeaturedFilmHomeServlet extends HttpServlet {

    private static final String VIEW = "/index.jsp";
    private static final String CURRENT_ATTR = "current";
    private static final String FEATURED_FILM = "selectedFeature";

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

//        EventDAO eventDAO = new EventDAO();
        FeaturedFilmDAO featureDAO = new FeaturedFilmDAO();
        this.getServletContext().getAttribute("current");

        String key = Utils.getLastUriToken(request);
        Config currentConfig = (Config) this.getServletContext().getAttribute(CURRENT_ATTR);
//        Film defaultFeature = filmDAO.find(eventDAO.find(currentConfig.getEvent()).getFilmKey());
        FeaturedFilm defaultFeature = featureDAO.find(currentConfig.getFeaturedFilmKey().getId());

        FeaturedFilm selectedFeature = null;
        if (!(CURRENT_ATTR).equals(key) || (CURRENT_ATTR + "/").equals(key)) {
            if (key != null && !"".equals(key)) {
                selectedFeature = featureDAO.query().filter("urlKey", key).get();
            }
        }

        if (selectedFeature == null) {
            selectedFeature = defaultFeature;
        }

        request.setAttribute(FEATURED_FILM, selectedFeature);
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
