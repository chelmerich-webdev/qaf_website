/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.series.deprecated;

import com.queerartfilm.dao.deprecated.SeriesDAO;
import com.googlecode.objectify.Key;
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
public class SeriesUpdateServlet extends HttpServlet {
    // Constants ----------------------------------------------------------------------------------

    private static final String VIEW = "/WEB-INF/jsp/update_series.jsp";
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_DTO_TYPE = "series";
    private static final String PARAM_ID = "id";
    // Vars ---------------------------------------------------------------------------------------
    private SeriesDAO seriesDAO;

    // HttpServlet actions ------------------------------------------------------------------------
    @Override
    public void init() throws ServletException {
        // Obtain the EventDAO from DAOFactory by Config.
//        this.eventDAO = Config.getInstance(getServletContext()).getDAOFactory().getQafSeriesEventDAO();
        this.seriesDAO = new SeriesDAO();
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

        // Show view.
        String id = FormUtil.getParamOrEmpty(request, PARAM_ID);
        Series series = null;
        if (!"".equals(id) && IsIntegerV.P.apply(id)) {
            series = seriesDAO.find(new Key<Series>(Series.class, Long.parseLong(id)));
        }

        if (series == null) { // we haven't got a valid event id or didn't have a valid id in the request
            series = new Series();
        }

        request.setAttribute(ATTRIBUTE_DTO_TYPE, series);
        request.getRequestDispatcher(VIEW).forward(request, response);
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



//        // Prepare form bean. Get a new form bean each time to validate the
        // current request. values will be set on the form
            SeriesFormV seriesForm = new SeriesFormV(seriesDAO);
            request.setAttribute(ATTRIBUTE_FORM, seriesForm);



        // Process request and get result.
        Series series = seriesForm.validateSeries(request);
        request.setAttribute(ATTRIBUTE_DTO_TYPE, series);

        // Postback.
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
