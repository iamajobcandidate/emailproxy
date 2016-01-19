package org.notanorg.email.service;

public interface EmailServiceProviderFactory {

  public EmailServiceProvider createPrimaryServiceProvider();

  public EmailServiceProvider createFallbackServiceProvider();
}
