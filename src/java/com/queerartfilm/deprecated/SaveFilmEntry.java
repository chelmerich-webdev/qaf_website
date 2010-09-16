package com.queerartfilm.deprecated;

import com.queerartfilm.deprecated.QafSeriesEventDAO;
import com.queerartfilm.deprecated.QafSeriesEventJDO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ch67dotnet
 */
@Deprecated
public class SaveFilmEntry extends HttpServlet {

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
        PrintWriter out = response.getWriter();

/*        Calendar screeningDateAndTime = Calendar.getInstance();
        screeningDateAndTime.set(Calendar.YEAR,
                Integer.parseInt(request.getParameter("screeningYear")));
        screeningDateAndTime.set(Calendar.MONTH,
                Integer.parseInt(request.getParameter("screeningMonth")));
        screeningDateAndTime.set(Calendar.DATE,
                Integer.parseInt(request.getParameter("screeningDay")));
        screeningDateAndTime.set(Calendar.HOUR,
                Integer.parseInt(request.getParameter("screeningHour")));
        screeningDateAndTime.set(Calendar.MINUTE, 0);
        screeningDateAndTime.set(Calendar.SECOND, 0);
        screeningDateAndTime.set(Calendar.AM_PM, Calendar.PM);


        FilmScreening film =
                new FilmScreening(request.getParameter("title"),
                screeningDateAndTime.getTime());
        Key key = KeyFactory.createKey(FilmScreening.class.getSimpleName(), createID(film.getTitle()));
        film.setKey(key);
        film.setYear(Integer.parseInt(request.getParameter("year")));
        film.setSynopsis(request.getParameter("synopsis"));
        film.setDirectorFirst(request.getParameter("directorFirst"));
        film.setDirectorLast(request.getParameter("directorLast"));

        film.setSeries(request.getParameter("series"));
        film.setScreeningVenue(request.getParameter("screeningVenue"));
        film.setVenueAddress1(request.getParameter("venueAddress1"));
        film.setVenueAddress2(request.getParameter("venueAddress2"));
        film.setVenuePhoneNumber(request.getParameter("venuePhoneNumber"));
        film.setPresenterFirst(request.getParameter("presenterFirst"));
        film.setPresenterLast(request.getParameter("presenterLast"));
*/
//        FilmScreening film = FilmScreeningFactory.newFilm(request);
//        FilmScreeningDAO.get().addFilm(film);

        QafSeriesEventJDO event = new QafSeriesEventJDO();
        event.getFilm().setTitle(request.getParameter("title"));
        QafSeriesEventDAO.get().addEvent(event);

        RequestDispatcher view = request.getRequestDispatcher("/archive/film?id=" + event.getKey().getName());
        view.forward(request, response);
    }


        /*
        for (String token : tokens) {
            for (char c : token.toCharArray()) {
                if (Character.isLetterOrDigit(c)) {
                    sb.append(c);
                }
                sb.append("-");
            }

        }*/



        // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
        /**
         * Handles the HTTP <code>GET</code> method.
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void
         doGet


        (HttpServletRequest request, HttpServletResponse response)
        throws ServletException,
        IOException
        {
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
        protected void
         doPost


        (HttpServletRequest request, HttpServletResponse response)
        throws ServletException,
        IOException
        {
            processRequest(request, response);
        }
        /**
         * Returns a short description of the servlet.
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo

             () {
        return "Short description";
        }// </editor-fold>

    }
