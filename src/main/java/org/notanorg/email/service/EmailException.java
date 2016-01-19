package org.notanorg.email.service;

public class EmailException extends Exception {

  private static final long serialVersionUID = -1182098457808141879L;

  public EmailException(Exception e) {
    super(e);
  }

  public EmailException(String message) {
    super(message);
  }
}
