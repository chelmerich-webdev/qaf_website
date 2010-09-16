package com.queerartfilm.film.deprecated;

import com.googlecode.objectify.Key;
import com.queerartfilm.dao.deprecated.FilmDAO;
import com.queerartfilm.film.Rating;
import com.queerartfilm.model.Utils;
import com.queerartfilm.validation.Form;
import com.queerartfilm.validation.FormUtil;
import com.queerartfilm.validation.IsGreaterThanV;
import com.queerartfilm.validation.IsNotEmptyOrNullV;
import com.queerartfilm.validation.IsWithinRangeV;
import com.queerartfilm.validation.ValidatorException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class FilmFormV extends Form {

    private static Logger logger = Logger.getLogger(FilmFormV.class.getName());
    // Constants ----------------------------------------------------------------------------------
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_YEAR = "year";
    private static final String FIELD_DIR_LAST = "directorLast";
    private static final String FIELD_DIR_FIRST = "directorFirst";
    private static final String FIELD_RATING = "mpaaRating";
    private static final String FIELD_LENGTH = "length";
    private static final String FIELD_RESULT = "result";
    private static final String FIELD_ID = "id";
    private static final String ZERO = "0";
    // Variables ----------------------------------------------------------------------------------
    private FilmDAO filmDAO;

    // Constructors -------------------------------------------------------------------------------
    /**
     * Construct an User Form associated with the given UserDAO.
     * @param userDAO The User DAO to be associated with the User Form.
     */
    public FilmFormV(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
    }

    // Form actions -------------------------------------------------------------------------------
    /**
     * Returns the registered Film based on the given request. It will gather all form fields,
     * process and validate the fields and save the created Film using the FilmDAODeprecated associated with
     * this form.
     *
     * @param request The request to register an Film with.
     * @return The registered Film based on the given request.
     */
    public Film validateFilm(HttpServletRequest request) {

        String param = FormUtil.getParamOrEmpty(request, "id");
        Film film = null;
        if (!("".equals(param) || "new".equals(param))) {
            film = filmDAO.find(param);
        }
        if (film == null) {
            film = FilmFactory.get().newFilm();
        }

        try {
            processTitle(request, film);
            processYear(request, film);
            processDirectorLast(request, film);
            processDirectorFirst(request, film);
            processMpaaRating(request, film);
            processLength(request, film);
            if (isValid()) {
                filmDAO.save(film);
                setMessage(FIELD_RESULT, SUCCESS_MSG);
                logger.info("Updated Film: " + film.getId());
            } else {
                setMessage(FIELD_RESULT, PENDING_MSG);
            }
        } catch (Exception ex) {
            String errorDetail = ex.getClass().getName() + ": " + ex.getMessage();
            setError(FIELD_RESULT, ERROR_MSG + "\nError: " + errorDetail);
            logger.warning(errorDetail);
            ex.printStackTrace();
        }
        return film;
    }

    // Field processors ---------------------------------------------------------------------------
    // the DAO sets the ID, but check for already existing
    void validateId(HttpServletRequest request, Film film, String title) throws ValidatorException {
        String param = FormUtil.getParamOrEmpty(request, FIELD_ID);

//        logger.info("ID in validateID: " + param);
        // check if id already exists on new submission
        if ("".equals(param) || "new".equals(param) ) {
            // if no id then check for existing
            String pendingId = Utils.createID(title);
//            logger.info("PendingID: " + pendingId);
            for (Key<Film> key : filmDAO.getAllItemKeys()) {
//                logger.info("Step thru IDs: " + key.getName());
                if (key.getName().equals(pendingId)) {
                    throw new ValidatorException(title + " is not a unique title.");
                }
            }
        }

    }



    public void processTitle(HttpServletRequest request, Film film) {
        String param = request.getParameter(FIELD_TITLE);
        boolean isValid = false;

        try {
            isValid = validateTitle(param);
            validateId(request, film, param);
        } catch (ValidatorException ex) {
            setError(FIELD_TITLE, ex.getMessage());
        } finally {
            if (isValid) {
                film.setTitle(param);
            } else {
                film.setTitle("");
            }
        }
    }

    public void processYear(HttpServletRequest request, Film film) {
        String param = request.getParameter(FIELD_YEAR);
        boolean isValid = false;
        try {
            isValid = validateYear(param);
        } catch (Exception ex) {
            setError(FIELD_YEAR, ex.getMessage());
        } finally {
            film.setYear(Integer.valueOf(isValid ? param : ZERO));
        }
    }

    /**
     * Process and validate the director's last name for the given film.
     *
     * @param request the given <code>HttpServletRequest</code>.
     * @param film the given <code>Film</code>.
     */
    public void processDirectorLast(HttpServletRequest request, Film film) {
        String param = request.getParameter(FIELD_DIR_LAST);
        boolean isValid = false;
        try {
            isValid = validateDirectorLast(param);
        } catch (ValidatorException ex) {
            setError(FIELD_DIR_LAST, ex.getMessage());
        } finally {
            film.setDirectorLast(isValid ? param : "");
        }

    }

    /**
     * Process and validate the director first name for the given film.
     * This field is optional and thus not validated.
     *
     * @param request the given <code>HttpServletRequest</code>.
     * @param film the given <code>Film</code>.
     */
    public void processDirectorFirst(HttpServletRequest request, Film film) {
        String param = request.getParameter(FIELD_DIR_FIRST);
        boolean isValid = false;
        try {
            isValid = validateDirectorFirst(param);
        } catch (ValidatorException ex) {
            setError(FIELD_DIR_FIRST, ex.getMessage());
        } finally {
            film.setDirectorFirst(isValid ? param : "");
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
    public void processMpaaRating(HttpServletRequest request, Film film) {
        String param = request.getParameter(FIELD_RATING);
        boolean isValid = false;
        try {
            isValid = validateMpaaRating(param);
        } catch (ValidatorException ex) {
            setError(FIELD_RATING, ex.getMessage());
        } finally {
            film.setMpaaRating(isValid ? Rating.valueOf(param) : Rating.NR);
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
    public void processLength(HttpServletRequest request, Film film) {
        String param = request.getParameter(FIELD_LENGTH);
        boolean isValid = false;
        try {
            isValid = validateLength(param);
        } catch (Exception ex) {
            setError(FIELD_LENGTH, ex.getMessage());
        } finally {
            film.setLength(Integer.valueOf(isValid ? param : ZERO));
        }
    }

    // Field validators ---------------------------------------------------------------------------
    private boolean validateTitle(String param) throws ValidatorException {
        return new IsNotEmptyOrNullV().validate(param);
    }

    private boolean validateYear(String param) throws ValidatorException {

        new IsNotEmptyOrNullV().validate(param);

        int min_year = 1900;
        int max_year = 1 + Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        String message =
                "Please enter a number between " + min_year + " and " + max_year + ".";
        IsWithinRangeV rangeValidator =
                new IsWithinRangeV(min_year, max_year, message);
        return rangeValidator.validate(param);
    }

    private boolean validateDirectorLast(String param) throws ValidatorException {
        return new IsNotEmptyOrNullV().validate(param);
    }

    private boolean validateDirectorFirst(String param) throws ValidatorException {
        return new IsNotEmptyOrNullV().validate(param);
    }

    private boolean validateMpaaRating(String param) throws ValidatorException {
        return new IsNotEmptyOrNullV().validate(param);
    }

    private boolean validateLength(String param) throws ValidatorException {
        IsNotEmptyOrNullV validator = new IsNotEmptyOrNullV();
        validator.validate(param);

        int min = 0;
        IsGreaterThanV greaterThanValidator = new IsGreaterThanV(min);
        return greaterThanValidator.validate(param);
    }
}
