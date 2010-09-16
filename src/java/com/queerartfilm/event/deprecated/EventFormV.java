/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.event.deprecated;

import com.queerartfilm.film.Venue;
import com.googlecode.objectify.Key;
import com.queerartfilm.dao.deprecated.EventDAO;
import com.queerartfilm.film.deprecated.Film;
import com.queerartfilm.validation.Form;
import com.queerartfilm.validation.FormUtil;
import com.queerartfilm.validation.IsNotEmptyOrNullV;
import com.queerartfilm.validation.ValidatorException;
import java.text.ParseException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class EventFormV extends Form {

    private static Logger logger = Logger.getLogger(EventFormV.class.getName());
    private static final String FIELD_FILM_KEY = "filmKey";
    private static final String FIELD_MONTH = "month";
    private static final String FIELD_DAY = "day";
    private static final String FIELD_YEAR = "year";
    private static final String FIELD_HOUR = "hour";
    private static final String FIELD_MINUTE = "minute";
    private static final String FIELD_AMPM = "ampm";
    private static final String FIELD_VENUE = "venue";
    private static final String FIELD_PRES_FIRST = "presenterFirst";
    private static final String FIELD_PRES_LAST = "presenterLast";
    private static final String FIELD_SYNOPSIS = "synopsis";
    private static final String FIELD_ID = "id";
    private static final String FIELD_RESULT = "result";
    // Variables ----------------------------------------------------------------------------------
    private EventDAO eventDAO;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct an User Form associated with the given UserDAO.
     * @param userDAO The User DAO to be associated with the User Form.
     */
    public EventFormV(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    // Form actions -------------------------------------------------------------------------------
    /**
     * Returns the registered Film based on the given request. It will gather all form fields,
     * process and validate the fields and save the created Film using the FilmDAO associated with
     * this form.
     *
     * @param request The request to register an Film with.
     * @return The registered Film based on the given request.
     */
    public Event validateEvent(HttpServletRequest request) {

        String param = FormUtil.getParamOrEmpty(request, FIELD_ID);
        Event event = null;
        if (!("".equals(param) || "new".equals(param))) {
            event = eventDAO.find(param);
        }
        if (event == null) {
            event = EventFactory.get().newEvent();
        }



        try {
            processFilmKey(request, event);
            processMonthAndYear(request, event);
            processDay(request, event);
            processHour(request, event);
            processMinute(request, event);
            processAmpm(request, event);
            processVenue(request, event);
            processPresenterFirst(request, event);
            processPresenterLast(request, event);
            processSynopsis(request, event);

            if (isValid()) {
                eventDAO.save(event);
                setMessage(FIELD_RESULT, SUCCESS_MSG);
                logger.info("Updated Event: " + event.getId());
            } else {
                setError(FIELD_RESULT, PENDING_MSG);
            }
        } catch (NullPointerException ex) {
            logger.warning(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            String errorDetail = ex.getClass().getName() + ": " + ex.getMessage();
            setError(FIELD_RESULT, ERROR_MSG + "\nError: " + errorDetail);
            logger.warning(errorDetail);
        }


        return event;
    }
//        try {
//            for (Field f : Field.values()) {
//                if (f == Field.SHOW_DATE_TIME) {
//                    continue;  // process this compound value at the end
//                }
//                processField(request, f, screening);
//            }

//
//            if (this.isValid() && dateIsValid) {
//
//                Field.SHOW_DATE_TIME.setScreeningField(screening, dateString);
//
//                eventDAO.add(screening);
//                setMessage(FIELD_RESULT, SUCCESS_MSG + "\n" + screening);
//                logger.info("Updated Screening: " + screening.getId());
//            } else {
//                setError(FIELD_RESULT, PENDING_MSG + " " + dateString + " is not a date.");
//            }
//        } catch (NullPointerException ex) {
//            logger.warning(ex.getMessage());
//            ex.printStackTrace();
//        } catch (Exception ex) {
//            String errorDetail = ex.getClass().getName() + ": " + ex.getMessage();
//            setError(FIELD_RESULT, ERROR_MSG + "\nError: " + errorDetail);
//            logger.warning(errorDetail);
//        }
//        return screening;
//    }
    // Field processors ---------------------------------------------------------------------------
//    @SuppressWarnings("unchecked")
    private void processFilmKey(HttpServletRequest request, Event event) {
        String param = request.getParameter(FIELD_FILM_KEY);
        boolean isValid = false;
        try {
            isValid = new IsNotEmptyOrNullV().validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_FILM_KEY, ex.getMessage());
        } finally {
            if (isValid) {
                Key<Film> filmKey = new Key<Film>(Film.class, param);
                event.setFilmKey(filmKey);
            }
        }
    }

    private void processMonthAndYear(HttpServletRequest request, Event event) {
        String MMMM = FormUtil.getParamOrEmpty(request, FIELD_MONTH);
        String yyyy = FormUtil.getParamOrEmpty(request, FIELD_YEAR);
        boolean isValid = false;
        try {
            isValid = validateId(request, event, MMMM, yyyy);
        } catch (ValidatorException ex) {
            setError(FIELD_ID, ex.getMessage());
        } catch (ParseException ex) {
            setError(FIELD_ID, "Month and Year are not formatted properly!");
        } finally {
            if (isValid) {
                event.setMonth(MMMM);
                event.setYear(yyyy);
            }
        }
    }

//    private void processMonth(HttpServletRequest request, Event event) {
//        String param = FormUtil.getParamOrEmpty(request, FIELD_MONTH);
//        boolean isValid = false;
//        try {
//            isValid = new IsNotEmptyOrNullV().validate(param);
//        } catch (ValidatorException ex) {
//            setError(FIELD_ID, ex.getMessage());
//        } finally {
//            event.setMonth(isValid ? param : "");
//        }
//    }

    // the DAO set's the ID, but check for already existing
    private boolean validateId(HttpServletRequest request, Event event, String MMMM, String yyyy)
            throws ParseException, ValidatorException {
        String param = FormUtil.getParamOrEmpty(request, FIELD_ID);
        logger.info("Requeset ID: " + param);
        boolean result = true;
        // do check if this is a new submission
        if ("".equals(param) || "new".equals(param)) {

            String pendingDate = String.format(Event.ID_STRING_FORMAT, MMMM, yyyy);
            long pendingId = Event.ID_DATE_FORMATTER.parse(pendingDate).getTime();
            logger.info("Pending ID: " + pendingId);
            for (Key<Event> key : eventDAO.getAllItemKeys()) {
                if (key.getId() == pendingId) {
                    result = false;
                    throw new ValidatorException(String.format(
                            "There is already a screening for %s %s.", MMMM, yyyy));
                }
            }
        }
        return result;
    }

    private void processDay(HttpServletRequest request, Event event) {
        String param = request.getParameter(FIELD_DAY);
        boolean isValid = false;
        try {
            isValid = new IsNotEmptyOrNullV().validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_DAY, ex.getMessage());
        } finally {
            event.setDay(isValid ? param : "");
        }
    }

    private void processHour(HttpServletRequest request, Event event) {
        String param = request.getParameter(FIELD_HOUR);
        boolean isValid = false;
        try {
            isValid = new IsNotEmptyOrNullV().validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_HOUR, ex.getMessage());
        } finally {
            event.setHour(isValid ? param : "");
        }
    }

    private void processMinute(HttpServletRequest request, Event event) {
        String param = request.getParameter(FIELD_MINUTE);
        boolean isValid = false;
        try {
            isValid = new IsNotEmptyOrNullV().validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_MINUTE, ex.getMessage());
        } finally {
            event.setMinute(isValid ? param : "");
        }
    }

    private void processAmpm(HttpServletRequest request, Event event) {
        String param = request.getParameter(FIELD_AMPM);
        boolean isValid = false;
        try {
            isValid = new IsNotEmptyOrNullV().validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_AMPM, ex.getMessage());
        } finally {
            event.setAmpm(isValid ? param : "");
        }
    }

    private void processVenue(HttpServletRequest request, Event event) {
        String param = request.getParameter(FIELD_VENUE);
        boolean isValid = false;
        try {
            isValid = new IsNotEmptyOrNullV().validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_VENUE, ex.getMessage());
        } finally {
            event.setVenue(Venue.valueOf(isValid ? param : "IFC_CENTER"));
        }
    }

    private void processPresenterFirst(HttpServletRequest request, Event event) {
        String param = request.getParameter(FIELD_PRES_FIRST);
        boolean isValid = false;
        try {
            isValid = new IsNotEmptyOrNullV().validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_PRES_FIRST, ex.getMessage());
        } finally {
            event.setPresenterFirst(isValid ? param : "");
        }
    }

    private void processPresenterLast(HttpServletRequest request, Event event) {
        String param = request.getParameter(FIELD_PRES_LAST);
        boolean isValid = false;
        try {
            isValid = new IsNotEmptyOrNullV().validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_PRES_LAST, ex.getMessage());
        } finally {
            event.setPresenterLast(isValid ? param : "");
        }
    }

    private void processSynopsis(HttpServletRequest request, Event event) {
        String param = request.getParameter(FIELD_SYNOPSIS);
        boolean isValid = false;
        try {
            isValid = new IsNotEmptyOrNullV().validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_SYNOPSIS, ex.getMessage());
        } finally {
            event.setSynopsis(isValid ? param : "");
        }
    }
//    private void processField(HttpServletRequest req, Field f, Screening screening) {
//        String param = req.getParameter(f.getFieldname());
//
//        if (f.validate(param)) {
//            f.setScreeningField(screening, param);
//        } else {
//            f.setScreeningField(screening, f.getDefaultValue());
//            setError(f.getFieldname(), "Please choose a value.");
//        }
//    }
}
