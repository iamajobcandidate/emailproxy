package org.notanorg.emailproxy;

import org.notanorg.email.service.EmailServiceProvider;
import org.notanorg.email.service.EmailServiceProviderFactory;
import org.notanorg.email.service.providers.MailgunClient;
import org.notanorg.email.service.providers.MailjetClient;

public class DefaultProviderFactory implements EmailServiceProviderFactory {

	public EmailServiceProvider createPrimaryServiceProvider() {
		return new MailgunClient();
	}

	public EmailServiceProvider createFallbackServiceProvider() {
		return new MailjetClient();
	}
}
