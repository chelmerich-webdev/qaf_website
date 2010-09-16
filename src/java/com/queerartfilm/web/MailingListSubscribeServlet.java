package com.queerartfilm.web;

import com.queerartfilm.validation.FormUtil;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ch67dotnet
 */
public class MailingListSubscribeServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MailingListSubscribeServlet.class.getName());
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
        
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "This is a test message!";
        String sendTo = FormUtil.getParamOrEmpty(request, "email");
        logger.info("SendTo: " + sendTo);
        String from = "ch67dev+subscribe@gmail.com";
        try {
             Message message = new MimeMessage(session);
             message.setFrom(new InternetAddress(from, "The QAF Team"));
             message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
             message.setSubject("Thank you for your request to join the QAF mailing list!");
             message.setText(msgBody);
             Transport.send(message);

        } catch (AddressException ex) {
            logger.warning("Address Exception: " + ex.getMessage());

        } catch (MessagingException ex) {
            logger.warning("Messagings Exception: " + ex.getMessage());
        }
        String returnPage = request.getParameter("return-page");
        RequestDispatcher view = request.getRequestDispatcher(returnPage);
        view.forward(request, response);
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
