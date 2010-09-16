package com.queerartfilm.film;

import com.googlecode.objectify.Key;
import com.queerartfilm.dao.FeaturedFilmDAO;
import com.queerartfilm.series.QAFSeries;
import com.queerartfilm.validation.Form;
import com.queerartfilm.validation.FormUtil;
import com.queerartfilm.validation.IsDateP;
import com.queerartfilm.validation.IsGreaterThanV;
import com.queerartfilm.validation.IsIntegerV;
import com.queerartfilm.validation.IsNotEmptyOrNullV;
import com.queerartfilm.validation.IsWithinRangeV;
import com.queerartfilm.validation.ValidatorException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * Java Bean for a <code>FeaturedFilm</code> web form submission. Handles
 * validation and persistence via a DAO.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class FeaturedFilmFormV extends Form {

    // Constants ----------------------------------------------------------------------------------
    private static Logger logger = Logger.getLogger(FeaturedFilmFormV.class.getName());
    // Form fieldname mappings
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_DIRECTOR = "director";
    private static final String FIELD_FIRST = "first";
    private static final String FIELD_LAST = "last";
    private static final String FIELD_RELEASE_YEAR = "releaseYear";
    private static final String FIELD_LENGTH = "length";
    private static final String FIELD_RATING = "rating";
    private static final String ERROR_Y_L_R_FLDSET =
            String.format("%s-%s-%s", FIELD_RELEASE_YEAR, FIELD_LENGTH, FIELD_RATING);
    private static final String FIELD_PRESENTER = "presenter";
    private static final String FIELD_LINKS = "links";
    private static final String FIELD_LABEL = "label";
    private static final String FIELD_URL = "url";
    private static final String FIELD_SYNOPSIS = "synopsis";
    private static final String FIELD_SERIES_KEY = "seriesKey";
    private static final String FIELD_SCREENING = "screening";
    private static final String FIELD_PURCHASE_URL = "purchaseUrl";
    private static final String FIELD_MONTH = "month";
    private static final String FIELD_DAY = "day";
    private static final String FIELD_YEAR = "year";
    private static final String FIELD_HOUR = "hour";
    private static final String FIELD_MINUTE = "minute";
    private static final String FIELD_AMPM = "ampm";
    private static final String FIELD_VENUE = "venue";
    private static final String FIELD_SECOND_TIME = "secondTime";
    private static final String FIELD_RESULT = "result";
//    private static final String FIELD_ID = "id";
    private static final String ZERO = "0";
    // Variables ----------------------------------------------------------------------------------
    private FeaturedFilmDAO dao;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct a <code>FeaturedFilmFormV</code> associated with the given DAO.
     * @param dao The <code>FeaturedFilmDAO</code> to be associated with
     * the <code>FeaturedFilmFormV</code> .
     */
    public FeaturedFilmFormV(FeaturedFilmDAO dao) {
        this.dao = dao;
    }

    // Form actions -------------------------------------------------------------------------------
    /**
     * Returns the registered <code>FeaturedFilm</code> based on the given request.
     * It will gather all form fields, process and validate the fields,
     * and persist the valid <code>FeaturedFilm</code> using the associate
     * <code>FeaturedFilmDAO</code>.
     *
     * @param request The request to register an <code>FeaturedFilm</code> with.
     * @return The registered Film based on the given request.
     */
    public FeaturedFilm validateFeaturedFilm(HttpServletRequest request) {

        FeaturedFilm ff = initFeaturedFilm(request);
        try {
            processAllFields(request, ff);
            if (this.isValid()) {
                processValidFeaturedFilm(ff);
            } else {
                reportInvalidFeaturedFilm();
            }
        } catch (Exception ex) {
            reportException(ex);
        }
        return ff;
    }
    // Primary Sub Methods ---------------------------------------------------------------------------

    private FeaturedFilm initFeaturedFilm(HttpServletRequest request) {
        String param = FormUtil.getParamOrEmpty(request, "id");
        FeaturedFilm ff = null;
//        if (!("".equals(param) || "new".equals(param))) {
        if (IsIntegerV.P.apply(param)) {
            try {
                long id = Long.parseLong(param);
                ff = dao.find(id);
            } catch (NumberFormatException ex) {
                logger.warning("Invalid QAFSeries id = " + param);
            }
        }
        if (ff == null) {
            ff = new FeaturedFilm();
        }
        return ff;
    }

    private void processAllFields(HttpServletRequest request, FeaturedFilm ff) {
        processTitle(request, ff);
        processDirectorFirst(request, ff);
        processDirectorLast(request, ff);
        processReleaseYear(request, ff);
        processRating(request, ff);
        processLength(request, ff);
        processPresenterFirst(request, ff);
        processPresenterLast(request, ff);
        processSynopsis(request, ff);
        processLinks(request, ff);
        processSeriesKey(request, ff);
        processScreeningDate(request, ff);
        processVenue(request, ff);
        processPurchaseUrl(request, ff);
        processSecondTime(request, ff);
    }

    private void processValidFeaturedFilm(FeaturedFilm ff) {
        Key<FeaturedFilm> newKey = dao.update(ff);
        ff = dao.ofy().get(newKey);
        setMessage(FIELD_RESULT, SUCCESS_MSG);
        logger.info("Updated Film: " + ff.getId());
    }

    private void reportInvalidFeaturedFilm() {
        setMessage(FIELD_RESULT, PENDING_MSG);
    }

    private void reportException(Exception ex) {
        String errorDetail = ex.getClass().getName() + ": " + ex.getMessage();
        setError(FIELD_RESULT, ERROR_MSG + "\nError: " + errorDetail);
        logger.warning(errorDetail);
        ex.printStackTrace();
    }

    // Field processors ---------------------------------------------------------------------------
    public void processTitle(HttpServletRequest request, FeaturedFilm film) {
        String param = request.getParameter(FIELD_TITLE);
        try {
            new IsNotEmptyOrNullV(String.format(ERROR_FMT_REQ, FIELD_TITLE)).validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_TITLE, ex.getMessage());
        } finally {
            film.setTitle(param);
        }
    }

    /**
     * Process and validate the director first name for the given film.
     * This field is optional and thus not validated.
     *
     * @param request the given <code>HttpServletRequest</code>.
     * @param film the given <code>Film</code>.
     */
    public void processDirectorFirst(HttpServletRequest request, FeaturedFilm film) {
        String param = FormUtil.getParamOrEmpty(
                request, String.format("%s.%s", FIELD_DIRECTOR, FIELD_FIRST));
        film.getDirector().setFirst(param);
    }

    /**
     * Process and validate the director's last name for the given film.
     *
     * @param request the given <code>HttpServletRequest</code>.
     * @param film the given <code>Film</code>.
     */
    public void processDirectorLast(HttpServletRequest request, FeaturedFilm film) {
        String fieldname = String.format("%s.%s", FIELD_DIRECTOR, FIELD_LAST);
        String param = FormUtil.getParamOrEmpty(request, fieldname);
        try {
            new IsNotEmptyOrNullV(
                    String.format(ERROR_FMT_REQ,
                    FIELD_DIRECTOR + "'s " + FIELD_LAST + " name")).validate(param);
        } catch (ValidatorException ex) {
            setError(fieldname, ex.getMessage());
        } finally {
            film.getDirector().setLast(param);
        }

    }

    public void processReleaseYear(HttpServletRequest request, FeaturedFilm film) {
        String param = FormUtil.getParamOrEmpty(request, FIELD_RELEASE_YEAR);
        boolean isValid = false;
        try {
            isValid = validateYear(param);
        } catch (Exception ex) {
            setError(FIELD_RELEASE_YEAR, ex.getMessage()); // set to invoke hilite on field
            addError(ERROR_Y_L_R_FLDSET, ex.getMessage()); // disply error for fieldset
        } finally {
            film.setReleaseYear(Integer.valueOf(isValid ? param : ZERO));
        }
    }

    /**
     * Process and validate the MPAA rating for the given film.
     * This field has a default value and selection is optional,
     * thus no validation occurs.
     *
     * @param request the given <code>HttpServletRequest</code>.
     * @param film the given <code>Film</code>.
     */
    public void processRating(HttpServletRequest request, FeaturedFilm film) {
        String param = FormUtil.getParamOrEmpty(request, FIELD_RATING);
        boolean isValid = false;
        try {
            isValid = new IsNotEmptyOrNullV().validate(param);
        } catch (ValidatorException ex) {
            addError(ERROR_Y_L_R_FLDSET, ex.getMessage());
        } finally {
            film.setRating(isValid ? Rating.valueOf(param) : Rating.NR);
        }
    }

    /**
     * Process and validate the length which is to be associated with the given film.
     * If the length is changed in the given film, then it will be validated.
     * The length will be set in the given film only if validation succeeds.
     *
     * @param request The request to get the age from.
     * @param user The User to be associated with the age.
     */
    public void processLength(HttpServletRequest request, FeaturedFilm film) {
        String param = request.getParameter(FIELD_LENGTH);
        boolean isValid = false;
        try {
            isValid = validateLength(param);
        } catch (Exception ex) {
            setError(FIELD_LENGTH, ex.getMessage()); // set to invoke hilite on field
            addError(ERROR_Y_L_R_FLDSET, ex.getMessage()); // disply error for fieldset
        } finally {
            film.setLength(Integer.valueOf(isValid ? param : ZERO));
        }
    }

    public void processPresenterFirst(HttpServletRequest request, FeaturedFilm film) {
        String param = FormUtil.getParamOrEmpty(
                request, String.format("%s.%s", FIELD_PRESENTER, FIELD_FIRST));
//        try {
//            new IsNotEmptyOrNullV().validate(param);
//        } catch (ValidatorException ex) {
//            setError(FIELD_PRESENTER, ex.getMessage());
//        } finally {
        film.getPresenter().setFirst(param);
//        }
    }

    public void processPresenterLast(HttpServletRequest request, FeaturedFilm film) {
        String fieldname = String.format("%s.%s", FIELD_PRESENTER, FIELD_LAST);
        String param = FormUtil.getParamOrEmpty(request, fieldname);
        try {
            new IsNotEmptyOrNullV(
                    String.format(ERROR_FMT_REQ,
                    FIELD_PRESENTER + "'s " + FIELD_LAST + " name")).validate(param);
        } catch (ValidatorException ex) {
            setError(fieldname, ex.getMessage());
        } finally {
            film.getPresenter().setLast(param);
        }
    }

    public void processLinks(HttpServletRequest request, FeaturedFilm film) {
        // multiple-value field
        String fieldname = String.format("%s.%s", FIELD_LINKS, FIELD_LABEL);
        String[] labelParams = FormUtil.getValuesOrEmptyArray(request, fieldname);

        fieldname = String.format("%s.%s", FIELD_LINKS, FIELD_URL);
        String[] urlParams = FormUtil.getValuesOrEmptyArray(request, fieldname);

        film.clearLinks();
        int len = labelParams.length;
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if (!labelParams[i].isEmpty()) {
                    film.addLink(new Link(labelParams[i], urlParams[i]));
                }
            }
        }
    }

    public void processSynopsis(HttpServletRequest request, FeaturedFilm film) {
        String param = FormUtil.getParamOrEmpty(request, FIELD_SYNOPSIS);
        try {
            new IsNotEmptyOrNullV(String.format(ERROR_FMT_REQ, FIELD_SYNOPSIS)).validate(param);
        } catch (ValidatorException ex) {
            setError(FIELD_SYNOPSIS, ex.getMessage());
        } finally {
            film.setSynopsis(param);
        }
    }

    public void processSeriesKey(HttpServletRequest request, FeaturedFilm film) {
        String param = FormUtil.getParamOrEmpty(request, FIELD_SERIES_KEY);
        film.setSeriesKey(
                "".equals(param)
                ? null
                : new Key<QAFSeries>(QAFSeries.class, Long.parseLong(param)));
    }

    public void processPurchaseUrl(HttpServletRequest request, FeaturedFilm film) {
        String param = FormUtil.getParamOrEmpty(
                request, String.format("%s.%s", FIELD_SCREENING, FIELD_PURCHASE_URL));
        film.getScreening().setPurchaseUrl(param);
    }

    public void processVenue(HttpServletRequest request, FeaturedFilm film) {
        String fieldname = String.format("%s.%s", FIELD_SCREENING, FIELD_VENUE);
        String param = FormUtil.getParamOrEmpty(request, fieldname);
        try {
            new IsNotEmptyOrNullV(String.format(ERROR_FMT_REQ, FIELD_VENUE)).validate(param);
        } catch (ValidatorException ex) {
            setError(fieldname, ex.getMessage());
        } finally {
            film.getScreening().setVenue(Venue.valueOf(param));
        }
    }

    public void processScreeningDate(HttpServletRequest request, FeaturedFilm film) {
        String pMonth = request.getParameter(
                String.format("%s.%s", FIELD_SCREENING, FIELD_MONTH));
        String pDay = request.getParameter(
                String.format("%s.%s", FIELD_SCREENING, FIELD_DAY));
        String pYear = request.getParameter(
                String.format("%s.%s", FIELD_SCREENING, FIELD_YEAR));
        String pHour = request.getParameter(
                String.format("%s.%s", FIELD_SCREENING, FIELD_HOUR));
        String pMinute = request.getParameter(
                String.format("%s.%s", FIELD_SCREENING, FIELD_MINUTE));
        String pAmpm = request.getParameter(
                String.format("%s.%s", FIELD_SCREENING, FIELD_AMPM));

        String dateString =
                String.format(Screening.defaultStringFormat,
                pMonth, pDay, pYear, pHour, pMinute, pAmpm);
        boolean isValid = false;
        try {
            isValid = new IsDateP().apply(pMonth, pDay, pYear, pHour, pMinute, pAmpm);
            if (!isValid) {
                throw new ValidatorException(String.format("%s %s is not a valid date!", pMonth, pDay));
            }
        } catch (ValidatorException ex) {
            setError(FIELD_SCREENING, ex.getMessage());
        } finally {
            try {
                if (isValid) {
                    film.getScreening().setDate(Screening.df.parse(dateString));
                } else {
                    // Reset day to 1 as error is mismatch date
                    film.getScreening().setDate(Screening.df.parse(
                            String.format(Screening.defaultStringFormat,
                            pMonth, "1", pYear, pHour, pMinute, pAmpm)));
                }
            } catch (ParseException ex) {
                logger.warning("Date Parse Exception: " + dateString);
            }
        }
    }

    public void processSecondTime(HttpServletRequest request, FeaturedFilm film) {
        String param = FormUtil.getParamOrEmpty(
                request, String.format("%s.%s", FIELD_SCREENING, FIELD_SECOND_TIME));
        film.getScreening().setSecondTime(param);
    }

// Field validators ---------------------------------------------------------------------------
    private boolean validateYear(String param) throws ValidatorException {
        new IsNotEmptyOrNullV(String.format(ERROR_FMT_REQ, FIELD_YEAR)).validate(param);
        int min_year = 1900;

        int max_year = 1 + Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        String message =
                "year must be between " + min_year + " and " + max_year;
        IsWithinRangeV rangeValidator =
                new IsWithinRangeV(min_year, max_year, message);

        return rangeValidator.validate(param);
    }

    private boolean validateLength(String param) throws ValidatorException {
        boolean result = false;
        result = new IsNotEmptyOrNullV(String.format(ERROR_FMT_REQ, FIELD_LENGTH)).validate(param);
        int min = 0;
        String msg = "length must be greater than " + min;
        result = new IsGreaterThanV(min, msg).validate(param);
        return result;
    }
}
