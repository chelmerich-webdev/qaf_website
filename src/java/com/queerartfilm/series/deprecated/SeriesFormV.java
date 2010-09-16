/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queerartfilm.series.deprecated;

import com.queerartfilm.dao.deprecated.SeriesDAO;
import com.queerartfilm.validation.Form;
import com.queerartfilm.validation.FormUtil;
import com.queerartfilm.validation.IsNotEmptyV;
import com.queerartfilm.validation.ValidatorException;
import java.util.Collections;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class SeriesFormV extends Form {

    private static Logger logger = Logger.getLogger(SeriesFormV.class.getName());
    // Constants ----------------------------------------------------------------------------------
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_EVENTS = "events";
    private static final String FIELD_ID = "id";
    private static final String FIELD_RESULT = "result";
    // Variables ----------------------------------------------------------------------------------
    private SeriesDAO seriesDAO;

    // Constructors -------------------------------------------------------------------------------

    public SeriesFormV(SeriesDAO seriesDAO) {
        this.seriesDAO = seriesDAO;
    }

    // Form actions -------------------------------------------------------------------------------
    /**
     * Returns the registered Series based on the given request. 
     *
     * @param request The request to save the Series with.
     * @return The saved Series based on the given request.
     */
    public Series validateSeries(HttpServletRequest request) {
        String param = FormUtil.getParamOrEmpty(request, FIELD_ID);
        Series series = null;
        if (!("".equals(param) || "new".equals(param))) {
            series = seriesDAO.find(param);
        }
        if (series == null) {
            series = new Series();
        }

        try {
            processTitle(request, series);
            processEvents(request, series);

            if (isValid()) {
                seriesDAO.save(series);
                setMessage(FIELD_RESULT, SUCCESS_MSG);
                logger.info(String.format(
                        "Updated Series record id=%s, %s: ",
                        series.getId(), series.getTitle()));
            } else {
                setMessage(FIELD_RESULT, PENDING_MSG);
            }
        } catch (Exception ex) {
            String errorDetail = ex.getClass().getName() + ": " + ex.getMessage();
            setError(FIELD_RESULT, ERROR_MSG + "\nError: " + errorDetail);
            logger.warning(errorDetail);
            ex.printStackTrace();
        }
        return series;
    }

    private void processTitle(HttpServletRequest request, Series series) {
        String param = FormUtil.getParamOrEmpty(request, FIELD_TITLE);
        try {
            new IsNotEmptyV().validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_TITLE, ex.getMessage());
        } finally {
            series.setTitle(param);
        }
    }

    @SuppressWarnings("unchecked")
    private void processEvents(HttpServletRequest request, Series series) {
        String[] params = request.getParameterValues(FIELD_EVENTS);
        // check for null or for empty array
        boolean isEmpty = false;
        try {
            isEmpty = FormUtil.isEmptyOrNull(params);
            if (isEmpty) {
                throw new ValidatorException("You must choose at least one screening.");
            }
        } catch (ValidatorException ex) {
            setError(FIELD_EVENTS, ex.getMessage());
        } finally {
            if (!isEmpty) {
                series.setEvents(Collections.EMPTY_LIST);
                for (String id : params) {
                    series.addEventsElement(id);
                }
            }
        }
    }
}
