package com.queerartfilm.deprecated;

import com.queerartfilm.deprecated.FilmScreeningDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet controller directs requests for '/archive/film?id=...' where id
 * is the id string for the film's key in the datastore.
 * 
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class GetFilmListing extends HttpServlet {

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

//        PersistenceManager pm = PMF.get().getPersistenceManager();
//        FilmScreening film = pm.getObjectById(FilmScreening.class, this.getKey(request));
//        request.setAttribute("film", film);
        request.setAttribute("film", FilmScreeningDAO.get().getFilm(request.getParameter("id")));
        RequestDispatcher view = request.getRequestDispatcher("/detail.jsp");
        view.forward(request, response);
        
    }

    private String getKey(HttpServletRequest request) {
        String key = request.getRequestURI();
        int idx = key.lastIndexOf("/");
        if (idx > 0) {
            key = key.substring(idx + 1);
        }

        return key;
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
