package com.queerartfilm.event.deprecated;

import com.googlecode.objectify.Key;
import com.queerartfilm.dao.deprecated.EventDAO;
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
public class EventUpdateServlet extends HttpServlet {
    // Constants ----------------------------------------------------------------------------------

    private static final String VIEW = "/WEB-INF/jsp/update_event.jsp";
    private static final String ATTRIBUTE_FORM = "form";
    private static final String ATTRIBUTE_DTO_TYPE = "event";
    private static final String PARAM_ID = "id";
    // Vars ---------------------------------------------------------------------------------------
    private EventDAO eventDAO;

    // HttpServlet actions ------------------------------------------------------------------------
    @Override
    public void init() throws ServletException {
        // Obtain the EventDAO from DAOFactory by Config.
//        this.eventDAO = Config.getInstance(getServletContext()).getDAOFactory().getQafSeriesEventDAO();
        this.eventDAO = new EventDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show view.
        String id = FormUtil.getParamOrEmpty(request, PARAM_ID);
        Event event = null;
        if (!("".equals(id) || "new".equals(id))) {
            if (IsIntegerV.P.apply(id))  {
                event = eventDAO.find(new Key<Event>(Event.class, Long.parseLong(id)));
            }
        }

        if (event == null) { // we haven't got a valid event id or didn't have one the request
            event = EventFactory.get().newEvent();
        }

        request.setAttribute(ATTRIBUTE_DTO_TYPE, event);
        request.getRequestDispatcher(VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Prepare form bean. Get a new form bean each time to validate the
        // current request. values will be set on the form
        EventFormV eventForm = new EventFormV(eventDAO);
        request.setAttribute(ATTRIBUTE_FORM, eventForm);

        // Process request and get result.
        Event event = eventForm.validateEvent(request);
        request.setAttribute(ATTRIBUTE_DTO_TYPE, event);

        // Postback.
        request.getRequestDispatcher(VIEW).forward(request, response);
    }
}
