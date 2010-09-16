package com.queerartfilm.validation;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class holds all (error) messages of a form and provides methods to return 
 * the 'success', the 'focus' and the 'highlight' of the processed form result.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jspservlet.html
 */
public class Form {

    public static final String SUCCESS_MSG = "You successfully updated this listing.";
    public static final String PENDING_MSG = "Correct the error(s) below and submit again.";
    public static final String ERROR_MSG = "Your submission failed due to database error.";
    public static final String ERROR_FMT_REQ = "%s is required";

    // Properties ---------------------------------------------------------------------------------
    private Map<String, String> messages = new LinkedHashMap<String, String>();
    private boolean hasError;

    // Getters ------------------------------------------------------------------------------------
    /**
     * Returns all messages of the form.
     * @return All messages of the form.
     */
    public Map<String, String> getMessages() {
        return messages;
    }

    /**
     * Returns true if there are no error messages.
     * @return True if there are no error messages.
     */
    public boolean isValid() {
        return !hasError;
    }

    /**
     * Returns the field name to set the first focus on.
     * @return The field name to set the first focus on.
     */
    public String getFocus() {
        return !messages.isEmpty() ? messages.keySet().iterator().next() : null;
    }

    /**
     * Returns the comma-separated field names to be highlighted.
     * @return The comma-separated field names to be highlighted.
     */
    public String getHighlight() {
        StringBuilder highlight = new StringBuilder();
        for (Iterator<String> fieldNames = messages.keySet().iterator(); fieldNames.hasNext();) {
            highlight.append(fieldNames.next());
            if (fieldNames.hasNext()) {
                highlight.append(",");
            }
        }
        return highlight.toString();
    }

    // Special setters ----------------------------------------------------------------------------
    /**
     * Set the given message on the given field name. This will overwrite any previously set
     * (error) message on the given field name.
     * @param fieldName The field name to set the given message on.
     * @param message The message to be set on the given field name.
     */
    public void setMessage(String fieldName, String message) {
        messages.put(fieldName,
                message.substring(0,1).toUpperCase() + message.substring(1));
    }

    /**
     * Adds message so that a multipart, concatenated message can be built.
     * If current message on fieldname is empty, then functions as
     * <code>#setMessage</code> would, otherwise adds an "and" and the
     * next message.
     */
     public void addMessage(String fieldName, String message) {
         String prevMsg = messages.get(fieldName);
         if (prevMsg == null) {
             setMessage(fieldName, message);
             return;
         }
         setMessage(fieldName, String.format("%s and %s", prevMsg, message));

     }
    /**
     * Set the given error message on the given field name. This will overwrite any previously set
     * (error) message on the given field name.
     * @param fieldName The field name to set the given error message on.
     * @param error The error message to be set on the given field name.
     */
    public void setError(String fieldName, String error) {
        hasError = true;
        setMessage(fieldName, error);
    }

    public void addError(String fieldName, String error) {
        hasError = true;
        addMessage(fieldName, error);
    }
}
