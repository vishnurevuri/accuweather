package com.accuweather.exception;

/**
 * Represents the any framework related errors.. 
 * @author  Vishnu
 * @version 1.0
 */
public class FrameworkException extends RuntimeException {

    //just for removing warning, the exceptions are not be expected to be java serialized
    private static final long serialVersionUID = -1L;
    
    /** 
     * Initialize with message and cause.
     * @param arg0
     * @param arg1
     */
    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Initialize with message alone.
     * @param arg0
     */
    public FrameworkException(String message) {
        super(message);
    }
    
}
