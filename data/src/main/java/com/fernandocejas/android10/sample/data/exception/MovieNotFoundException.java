/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.data.exception;

/**
 * Exception throw by the application when a Movie search can't return a valid result.
 */
public class MovieNotFoundException extends Exception {

  public MovieNotFoundException() {
    super();
  }

  public MovieNotFoundException(final String message) {
    super(message);
  }

  public MovieNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public MovieNotFoundException(final Throwable cause) {
    super(cause);
  }
}
