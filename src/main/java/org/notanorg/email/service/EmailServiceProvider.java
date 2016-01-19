package org.notanorg.email.service;

public interface EmailServiceProvider {

	public void send(EmailSendRequest request) throws EmailException;
}
