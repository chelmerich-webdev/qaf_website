package com.queerartfilm.series;

import com.googlecode.objectify.Key;
import com.queerartfilm.dao.QAFSeriesDAO;
import com.queerartfilm.film.FeaturedFilm;
import com.queerartfilm.validation.Form;
import com.queerartfilm.validation.FormUtil;
import com.queerartfilm.validation.IsNotEmptyV;
import com.queerartfilm.validation.ValidatorException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class QAFSeriesFormV extends Form {

    private static Logger logger = Logger.getLogger(QAFSeriesFormV.class.getName());
    // Constants ----------------------------------------------------------------------------------
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_FILM_KEYS = "filmKeys";
    private static final String FIELD_ID = "id";
    private static final String FIELD_RESULT = "result";
    // Variables ----------------------------------------------------------------------------------
    private QAFSeriesDAO dao;

    // Constructors -------------------------------------------------------------------------------
    public QAFSeriesFormV(QAFSeriesDAO dao) {
        this.dao = dao;
    }

    // Form actions -------------------------------------------------------------------------------
    /**
     * Returns the registered Series based on the given request. 
     *
     * @param request The request to save the Series with.
     * @return The saved Series based on the given request.
     */
    public QAFSeries validateSeries(HttpServletRequest request) {
        String param = FormUtil.getParamOrEmpty(request, FIELD_ID);
        QAFSeries qafSeries = null;
        if (!("".equals(param) || "new".equals(param))) {
            try {
                long id = Long.parseLong(param);
                qafSeries = dao.find(id);
            } catch (NumberFormatException ex) {
                logger.warning("Invalid QAFSeries id = " + param);
            }
        }
        if (qafSeries == null) {
            qafSeries = new QAFSeries();
        }

        try {
            processTitle(request, qafSeries);
            processFilmKeys(request, qafSeries);

            if (isValid()) {
                Key<QAFSeries> newKey = dao.update(qafSeries);
                qafSeries = dao.ofy().get(newKey);
                setMessage(FIELD_RESULT, SUCCESS_MSG);
                logger.info(String.format(
                        "Updated QAFSeries id=%s, %s: ",
                        qafSeries.getId(), qafSeries.getTitle()));
            } else {
                setMessage(FIELD_RESULT, PENDING_MSG);
            }
        } catch (Exception ex) {
            String errorDetail = ex.getClass().getName() + ": " + ex.getMessage();
            setError(FIELD_RESULT, ERROR_MSG + "\nError: " + errorDetail);
            logger.warning(errorDetail);
            ex.printStackTrace();
        }
        return qafSeries;
    }

    private void processTitle(HttpServletRequest request, QAFSeries series) {
        String param = FormUtil.getParamOrEmpty(request, FIELD_TITLE);
        try {
            new IsNotEmptyV(String.format(ERROR_FMT_REQ, "Series Name")).validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_TITLE, ex.getMessage());
        } finally {
            series.setTitle(param);
        }
    }

    private void processFilmKeys(HttpServletRequest request, QAFSeries series) {
        // multiple-value field
        String[] params = FormUtil.getValuesOrEmptyArray(request, FIELD_FILM_KEYS);

        series.clearFilmKeys();
        if (params.length > 0) {
            for (String id : params) {
                series.addFilmKey(new Key<FeaturedFilm>(FeaturedFilm.class, Long.parseLong(id)));
            }
        }
    }
}

