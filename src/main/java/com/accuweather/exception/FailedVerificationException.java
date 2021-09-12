/**
 * 
 */
package com.accuweather.exception;

/**
 * Class represents the failed action exceptions that will be thrown
 * 
 * @author MSivap
 * @version 1.0
 */
public class FailedVerificationException extends RuntimeException {

  /**
   * Serial version uid
   */
  private static final long serialVersionUID = 1L;

  /**
   * Initialize with error message
   * 
   * @param message
   */
  public FailedVerificationException(String message) {
    super(message);
  }

  /**
   * Initialized with error message and cause for the exceptions.
   * 
   * @param message
   * @param cause
   */
  public FailedVerificationException(String message, Throwable cause) {
    super(message, cause);
  }
  


}
