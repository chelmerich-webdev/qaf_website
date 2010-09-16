package com.queerartfilm.validation;

/**
 * This class represents a generic validator exception. It should be thrown by 
 * all validation actions in the controller.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jspservlet.html
 */
public class ValidatorException extends Exception {

    // Constructors -------------------------------------------------------------------------------

    /**
     * Constructs a ValidatorException with the given detail message.
     * @param message The detail message of the ValidatorException.
     */
    public ValidatorException(String message) {
        super(message);
    }

}