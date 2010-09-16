package com.queerartfilm.validation;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class for forms. This class contains commonly used request parameter processing and
 * validation logic which are been refactored in single static methods.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jspservlet.html
 */
public final class FormUtil {

    private static Logger logger = Logger.getLogger(FormUtil.class.getName());
    // Constructors -------------------------------------------------------------------------------

    private FormUtil() {
        // Utility class, hide constructor.
    }

    // Request parameter processing ---------------------------------------------------------------

    public static String[] getValuesOrEmptyArray(HttpServletRequest request, String fieldName) {
        String[] values = request.getParameterValues(fieldName);
        if (values == null) {
            return new String[0];
        } else {
            return values;
        }
    }
    /**
     * Returns the form field value from the given request associated with the given field name.
     * It returns the empty string if the form field value is null or is empty after trimming all whitespace.
     * 
     * @param request The request to return the form field value for.
     * @param fieldName The field name to be associated with the field value.
     * @return The form field value from the given request associated with the given field name.
     */
    public static String getParamOrEmpty(HttpServletRequest request, String fieldName) {
        String value = request.getParameter(fieldName);
        return isEmptyOrNull(value) ? "" : value;
    }

        /**
     * Returns the form field value from the given request associated with the given field name.
     * It returns null if the form field value is null or is empty after trimming all whitespace.
     *
     * @param request The request to return the form field value for.
     * @param fieldName The field name to be associated with the field value.
     * @return The form field value from the given request associated with the given field name.
     */
    public static String getParamOrNull(HttpServletRequest request, String fieldName) {
        String value = request.getParameter(fieldName);
        return isEmptyOrNull(value) ? null : value;
    }

    // Validation ---------------------------------------------------------------------------------

    /**
     * Returns true if the given value is null or is empty.
     * @param value The value to be checked on emptiness.
     * @return True if the given value is null or is empty.
     */
    public static boolean isEmptyOrNull(Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            return ((String) value).trim().length() == 0;
        } else if (value instanceof Object[]) {
            return ((Object[]) value).length == 0;
        } else if (value instanceof Collection<?>) {
            return ((Collection<?>) value).size() == 0;
        } else if (value instanceof Map<?, ?>) {
            return ((Map<?, ?>) value).size() == 0;
        } else {
            return value.toString() == null || value.toString().trim().length() == 0;
        }
    }

    /**
     * Returns true if the given old value does not equals the given new value.
     *
     * @param oldValue The old value to be compared with the new value. The value may be null.
     * @param newValue The new value to be compared with the old value. The value may be null.
     * @return True if the given old value does not equals the given new value.
     */
    public static boolean isChanged(Object oldValue, Object newValue) {
        boolean test =  oldValue == null
                        ? newValue != null
                        : !oldValue.equals(newValue);
        return test;
    }

    /**
     * Returns true if the given string is a valid email address.
     *
     * @param string The string to be checked on being a vaild email address.
     * @return True if the given string is a valid email address.
     */
    public static boolean isEmail(String string) {
        return string.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");
    }

    /**
     * Returns true if the given string is a valid positive number.
     *
     * @param string The <code>String</code> to be checked on being a vaild positive number.
     * @return <code>true</code> if the given string is a valid positive number.
     */
    public static boolean isNumber(String string) {
        return string.matches("^\\d+$");
    }

}
