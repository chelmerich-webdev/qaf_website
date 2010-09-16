/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.series;

import com.googlecode.objectify.Key;
import com.queerartfilm.dao.QAFSeriesDAO;
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
public class QAFSeriesUpdateServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final String VIEW = "/WEB-INF/jsp/update_qafseries.jsp";
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_DTO_TYPE = "qafSeries";
    private static final String PARAM_ID = "id";
    // Vars ---------------------------------------------------------------------------------------
    private QAFSeriesDAO qafSeriesDAO;

    // HttpServlet actions ------------------------------------------------------------------------
    @Override
    public void init() throws ServletException {
        // Obtain the EventDAO from DAOFactory by Config.
//        this.eventDAO = Config.getInstance(getServletContext()).getDAOFactory().getQafSeriesEventDAO();
        this.qafSeriesDAO = new QAFSeriesDAO();
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
        response.setContentType("text/html;charset=UTF-8");

        // Show view.
        String id = FormUtil.getParamOrEmpty(request, PARAM_ID);
        QAFSeries qafSeries = null;
        if (IsIntegerV.P.apply(id)) {
            qafSeries = qafSeriesDAO.find(Long.parseLong(id));
        }

        if (qafSeries == null) { // we haven't got a valid event id or didn't have a valid id in the request
            qafSeries = new QAFSeries();
        }

        request.setAttribute(ATTRIBUTE_DTO_TYPE, qafSeries);
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
        response.setContentType("text/html;charset=UTF-8");

        // Prepare form bean. Get a new form bean each time to validate the
        // current request. values will be set on the form
        QAFSeriesFormV qafSeriesForm = new QAFSeriesFormV(qafSeriesDAO);
        request.setAttribute(ATTRIBUTE_FORM, qafSeriesForm);



        // Process request and get result.
        QAFSeries qafSeries = qafSeriesForm.validateSeries(request);
        request.setAttribute(ATTRIBUTE_DTO_TYPE, qafSeries);

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
    }// </editor-fold>
}
