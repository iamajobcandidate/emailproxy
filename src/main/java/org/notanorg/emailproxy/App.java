package org.notanorg.emailproxy;

import java.util.concurrent.TimeUnit;

import org.notanorg.email.service.EmailException;
import org.notanorg.email.service.EmailSendRequest;
import org.notanorg.email.service.EmailServiceProvider;
import org.notanorg.email.service.EmailServiceProviderFactory;

public class App implements EmailServiceProvider {

  private EmailServiceProvider primary;
  private EmailServiceProvider fallback;
  private Object fallbackLock = new Object();
  private boolean fallenBack;
  private long fallbackTime;

  public App(EmailServiceProviderFactory factory) {
    primary = factory.createPrimaryServiceProvider();
    fallback = factory.createFallbackServiceProvider();
  }

  public void send(EmailSendRequest request) throws EmailException {
    if (!isUseFallback()) {
      try {
        primary.send(request);
      } catch (Exception e) {
        setFallenBack();
      }
    }

    fallback.send(request);
  }

  private synchronized void setFallenBack() {
    this.fallenBack = true;
    this.fallbackTime = System.currentTimeMillis();
  }

  private boolean isUseFallback() {
    // an ugly mutating getter to reset the flag, once we're past a certain
    // amount of time
    long now = System.currentTimeMillis();
    if (fallenBack && (now - fallbackTime) >= getResetInterval()) {
      synchronized (fallbackLock) {
        fallenBack = false;
      }
    }

    return fallenBack;
  }

  private long getResetInterval() {
    return TimeUnit.SECONDS.toMillis(30);
  }
}