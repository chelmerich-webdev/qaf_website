package com.queerartfilm.event.deprecated;

import com.googlecode.objectify.Key;
import com.queerartfilm.dao.deprecated.EventDAO;
import com.queerartfilm.validation.FormUtil;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ch67dotnet
 */
public class EventDeleteServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final Logger logger = Logger.getLogger(EventDeleteServlet.class.getName());
    private static final String VIEW = "/WEB-INF/jsp/delete_confirm.jsp";
    private static final String PARAM_ID = "id";
    // Vars ---------------------------------------------------------------------------------------
    private EventDAO eventDAO;

    // HttpServlet actions ------------------------------------------------------------------------
    @Override
    public void init() throws ServletException {
        this.eventDAO = new EventDAO();
    }

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
        // Show view.
        String id = FormUtil.getParamOrEmpty(request, PARAM_ID);
        Event event = null;
        if (!"".equals(id)) {
            event = eventDAO.find(id);
        }

        String message = String.format(
                "The attempt to delete the record for Screening id=" +
                "<strong>%s</strong> failed because it ", id);
        String result = "Failure";

        if (event == null) {
            message += "was not found.";
            request.setAttribute("message", message);
        } else if (event.isAssigned()) {
            message += "is currently assigned to a series.";
        } else {
            eventDAO.delete(event);
            result = "Success";
            message = String.format(
                    "The Screening record for <strong>%s</strong> " +
                    "(id=<strong>%s</strong>) has been deleted successfully.",
                    event, id);
            logger.info(String.format("Deleted Screening: id=%s", id));
        }


        if (result.equals("Failure")) {
            logger.warning(String.format("Screening delete failed: id=%s", id));
        }

        request.setAttribute("result", result);
        request.setAttribute("message", message);
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
